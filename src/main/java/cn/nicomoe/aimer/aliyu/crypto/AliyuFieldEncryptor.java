package cn.nicomoe.aimer.aliyu.crypto;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * 阿里鱼小程序字段级 RSA 加密器。
 * <p>
 * 复现小程序 request 封装内的 {@code w}（单字段加密）与 {@code D}（递归遍历）函数：
 * 命中 R 表的字段用 RSA-2048（PKCS1 v1.5）加密并输出 Base64 密文，对象 / 数组按层级递归，
 * 未命中字段原样保留。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@Component
public class AliyuFieldEncryptor {

    private static final String PEM_BEGIN = "-----BEGIN PUBLIC KEY-----";

    private static final String PEM_END = "-----END PUBLIC KEY-----";

    /**
     * RSA 公钥 classpath 路径（X.509 SubjectPublicKeyInfo，PEM 格式）。
     * <p>
     * 公钥为公开信息，提取自小程序源码常量 {@code x}，可安全随代码分发。
     * </p>
     */
    private static final String PUBLIC_KEY_PATH = "/aliyu/aliyu_pubkey.pem";

    /**
     * RSA 变换算法，PKCS1 v1.5 padding，与小程序 JSEncrypt-like 库默认行为一致
     */
    private static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    /**
     * RSA 公钥，实例创建时从 classpath 读取一次
     */
    private final RSAPublicKey publicKey;

    public AliyuFieldEncryptor() {
        this.publicKey = loadPublicKey();
    }

    /**
     * 加密单个字段，等价小程序 {@code w(e)}，输出 Base64 密文（2048 位 RSA 为 344 字符）。
     *
     * @param plain 明文字段值
     * @return Base64 密文
     */
    public String encryptField(String plain) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8)));
        } catch (GeneralSecurityException e) {
            // 与小程序不同：不降级发明文，避免敏感字段以明文泄露到上游
            throw new EruptWebApiRuntimeException("阿里鱼请求字段加密失败", e);
        }
    }

    /**
     * 处理单个键值对，等价小程序 {@code D} 函数对某个键的分支。
     * <p>
     * 容器优先递归（容器本身不会被整块加密），命中字段且值为非空字符串 / 非零数字时才加密。
     * </p>
     *
     * @param name   字段名
     * @param value  字段值
     * @param fields 该接口需加密的字段名集合
     * @return 处理后的值
     */
    public Object encryptEntry(String name, Object value, Set<String> fields) {
        if (isContainer(value)) {
            return encryptContainer(value, fields);
        }
        if (Objects.nonNull(name) && fields.contains(name) && isEncryptable(value)) {
            return encryptField(String.valueOf(value));
        }
        return value;
    }

    /**
     * 处理非容器请求体对象（如 {@code @JSONBody} 的 DTO），先转成 Map 再递归处理。
     *
     * @param body      请求体对象
     * @param fields    该接口需加密的字段名集合
     * @param toMapFunc 非容器对象转 Map 的函数（通常传 Forest 的 JSON 转换器）
     * @return 处理后的 Map；转换失败时返回原对象
     */
    public Object encryptBodyObject(Object body, Set<String> fields, Function<Object, Map<String, Object>> toMapFunc) {
        if (Objects.isNull(body)) {
            return null;
        }
        if (isContainer(body)) {
            return encryptContainer(body, fields);
        }
        Map<String, Object> bodyMap = toMapFunc.apply(body);
        if (Objects.isNull(bodyMap)) {
            return body;
        }
        Object encrypted = encryptContainer(bodyMap, fields);
        // 无字段命中时返回原对象，交回 Forest 按原 DTO 序列化，避免 Map 化改变输出结构
        return Objects.equals(encrypted, bodyMap) ? body : encrypted;
    }

    /**
     * 递归处理 Map / List / 数组，等价小程序 {@code D} 函数。
     *
     * @param value  容器对象
     * @param fields 该接口需加密的字段名集合
     * @return 处理后的新容器
     */
    private Object encryptContainer(Object value, Set<String> fields) {
        if (value instanceof Map<?, ?> map) {
            Map<String, Object> result = new LinkedHashMap<>(map.size());
            map.forEach((key, item) -> {
                String name = String.valueOf(key);
                result.put(name, this.encryptEntry(name, item, fields));
            });
            return result;
        }
        if (value instanceof List<?> list) {
            List<Object> result = new ArrayList<>(list.size());
            for (Object item : list) {
                result.add(this.encryptEntry(null, item, fields));
            }
            return result;
        }
        Object[] array = (Object[]) value;
        Object[] result = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = this.encryptEntry(null, array[i], fields);
        }
        return result;
    }

    /**
     * 判断是否需要加密：仅非空字符串 / 非零数字，与小程序 {@code e[n] && (...)} 的真值判断一致。
     */
    private boolean isEncryptable(Object value) {
        if (value instanceof CharSequence text) {
            return StrUtil.isNotEmpty(text);
        }
        if (value instanceof Number number) {
            return number.doubleValue() != 0d;
        }
        return false;
    }

    private boolean isContainer(Object value) {
        return value instanceof Map<?, ?> || value instanceof List<?> || value instanceof Object[];
    }

    private static RSAPublicKey loadPublicKey() {
        try (InputStream stream = AliyuFieldEncryptor.class.getResourceAsStream(PUBLIC_KEY_PATH)) {
            if (Objects.isNull(stream)) {
                throw new EruptWebApiRuntimeException("未找到阿里鱼 RSA 公钥文件：" + PUBLIC_KEY_PATH);
            }
            String pem = IoUtil.readUtf8(stream);
            String base64 = StrUtil.cleanBlank(pem
                    .replace(PEM_BEGIN, StrUtil.EMPTY)
                    .replace(PEM_END, StrUtil.EMPTY));
            byte[] der = Base64.getDecoder().decode(base64);
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(der));
        } catch (IOException | GeneralSecurityException e) {
            throw new EruptWebApiRuntimeException("加载阿里鱼 RSA 公钥失败", e);
        }
    }
}