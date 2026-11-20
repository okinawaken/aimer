package cn.nicomoe.aimer.aliyu.service.crypto.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.service.crypto.AliyuRsaService;
import org.springframework.stereotype.Service;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 阿里鱼小程序字段级 RSA 加密服务实现。
 * <p>按字段名递归遍历对象（数组、集合、Map、嵌套对象），命中字段且为字符串时原地替换为密文。
 */
@Service
public class AliyuRsaServiceImpl implements AliyuRsaService {

    /**
     * 仅反射遍历本项目包下的对象，避免进入 JDK 或框架类。
     */
    private static final String PROJECT_PACKAGE_PREFIX = "cn.nicomoe.aimer.";

    /**
     * 小程序内置公钥的 RSA 加密器，PKCS#1 v1.5 填充。
     */
    private static final RSA RSA_ENCRYPTOR = new RSA(null, AliyuApiConstants.Rsa.PUBLIC_KEY);

    @Override
    public String encrypt(String plainText) {
        return RSA_ENCRYPTOR.encryptBase64(plainText, KeyType.PublicKey);
    }

    @Override
    public void encryptFields(Object target, Collection<String> fieldNames) {
        if (Objects.isNull(target) || CollUtil.isEmpty(fieldNames)) {
            return;
        }
        this.encryptObject(target, CollUtil.newHashSet(fieldNames),
                Collections.newSetFromMap(new IdentityHashMap<>()));
    }

    private void encryptObject(Object target, Set<String> fieldNames, Set<Object> visited) {
        if (Objects.isNull(target) || !visited.add(target)) {
            return;
        }
        Class<?> clazz = target.getClass();
        if (clazz.isArray()) {
            int length = Array.getLength(target);
            for (int index = 0; index < length; index++) {
                this.encryptObject(Array.get(target, index), fieldNames, visited);
            }
            return;
        }
        if (target instanceof Iterable<?> iterable) {
            iterable.forEach(item -> this.encryptObject(item, fieldNames, visited));
            return;
        }
        if (target instanceof Map<?, ?> map) {
            map.values().forEach(item -> this.encryptObject(item, fieldNames, visited));
            return;
        }
        if (this.isSimpleValue(target) || !clazz.getName().startsWith(PROJECT_PACKAGE_PREFIX)) {
            return;
        }
        for (Class<?> current = clazz; Objects.nonNull(current) && !Objects.equals(current, Object.class);
             current = current.getSuperclass()) {
            for (Field field : current.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                this.encryptField(target, field, fieldNames, visited);
            }
        }
    }

    private void encryptField(Object target, Field field, Set<String> fieldNames, Set<Object> visited) {
        try {
            field.setAccessible(true);
            Object value = field.get(target);
            if (Objects.isNull(value)) {
                return;
            }
            if (fieldNames.contains(field.getName()) && Objects.equals(field.getType(), String.class)
                    && value instanceof CharSequence && StrUtil.isNotEmpty((CharSequence) value)) {
                field.set(target, this.encrypt(value.toString()));
            } else {
                this.encryptObject(value, fieldNames, visited);
            }
        } catch (IllegalAccessException e) {
            throw new EruptWebApiRuntimeException("阿里鱼请求字段加密失败：" + e.getMessage());
        }
    }

    private boolean isSimpleValue(Object value) {
        return value instanceof CharSequence || value instanceof Number || value instanceof Boolean
                || value instanceof Character || value instanceof Enum<?> || value instanceof java.util.Date
                || value instanceof java.time.temporal.TemporalAccessor;
    }
}
