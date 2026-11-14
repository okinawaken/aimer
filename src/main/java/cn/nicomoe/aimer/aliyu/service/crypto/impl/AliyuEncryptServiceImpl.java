package cn.nicomoe.aimer.aliyu.service.crypto.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.service.crypto.AliyuEncryptService;
import cn.nicomoe.aimer.util.CheckUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
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

/** 阿里鱼请求字段加密服务实现。 */
@Service
public class AliyuEncryptServiceImpl implements AliyuEncryptService {

    private static final String PEM_BEGIN = "-----BEGIN PUBLIC KEY-----";
    private static final String PEM_END = "-----END PUBLIC KEY-----";
    private static final String PUBLIC_KEY_PATH = "/public/aliyu/pem/aliyu_pubkey.pem";
    private static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    private RSAPublicKey publicKey;

    @PostConstruct
    public void init() {
        this.publicKey = this.loadPublicKey();
    }

    @Override
    public String encryptField(String plain) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8)));
        } catch (GeneralSecurityException e) {
            throw new EruptWebApiRuntimeException("阿里鱼请求字段加密失败", e);
        }
    }

    @Override
    public Object encryptEntry(String name, Object value, Set<String> fields) {
        if (this.isContainer(value)) {
            return this.encryptContainer(value, fields);
        }
        if (Objects.nonNull(name) && fields.contains(name) && this.isEncryptable(value)) {
            return this.encryptField(String.valueOf(value));
        }
        return value;
    }

    @Override
    public Object encryptBodyObject(Object body, Set<String> fields, Function<Object, Map<String, Object>> toMapFunc) {
        if (Objects.isNull(body)) {
            return null;
        }
        if (this.isContainer(body)) {
            return this.encryptContainer(body, fields);
        }
        Map<String, Object> bodyMap = toMapFunc.apply(body);
        if (Objects.isNull(bodyMap)) {
            return body;
        }
        Object encrypted = this.encryptContainer(bodyMap, fields);
        return Objects.equals(encrypted, bodyMap) ? body : encrypted;
    }

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

    private RSAPublicKey loadPublicKey() {
        try (InputStream stream = AliyuEncryptServiceImpl.class.getResourceAsStream(PUBLIC_KEY_PATH)) {
            CheckUtils.throwIfNull(stream, "未找到阿里鱼 RSA 公钥文件：" + PUBLIC_KEY_PATH);
            String pem = IoUtil.readUtf8(stream);
            String base64 = StrUtil.cleanBlank(StrUtil.removeAll(StrUtil.removeAll(pem, PEM_BEGIN), PEM_END));
            byte[] der = Base64.getDecoder().decode(base64);
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(der));
        } catch (IOException | GeneralSecurityException e) {
            throw new EruptWebApiRuntimeException("加载阿里鱼 RSA 公钥失败", e);
        }
    }
}