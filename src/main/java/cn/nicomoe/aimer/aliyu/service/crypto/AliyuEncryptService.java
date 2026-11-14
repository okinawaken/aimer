package cn.nicomoe.aimer.aliyu.service.crypto;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/** 递归加密指定请求字段，使用 RSA-2048（PKCS1 v1.5）并输出 Base64 密文。 */
public interface AliyuEncryptService {

    /** 加密单个字段，输出 Base64 密文。 */
    String encryptField(String plain);

    /** 处理单个键值对，容器优先递归，容器本身不整体加密； 命中字段且值为非空字符串 / 非零数字时才加密。 */
    Object encryptEntry(String name, Object value, Set<String> fields);

    /** 处理非容器请求体对象（如 {@code @JSONBody} 的 DTO），先转成 Map 再递归处理。 */
    Object encryptBodyObject(Object body, Set<String> fields, Function<Object, Map<String, Object>> toMapFunc);
}