package cn.nicomoe.aimer.aliyu.service.crypto;

import java.util.Collection;

/**
 * 阿里鱼小程序字段级 RSA 加密服务。
 * <p>小程序请求封装在发送前会按接口路径对指定字段逐个加密（PKCS#1 v1.5），密钥硬编码在小程序包内；
 * 未加密时上游返回“参数错误”。风控头 {@code hecate} 仍按加密前的明文计算，故加密须在风控头计算之后执行。
 */
public interface AliyuRsaService {

    /**
     * 对单个明文做 RSA 加密。
     * 参数 plainText：待加密明文
     * 返回：Base64 编码的密文
     */
    String encrypt(String plainText);

    /**
     * 对对象中指定字段名的字符串值做 RSA 加密，与小程序端逐字段加密行为一致。
     * 参数 target：待加密的请求对象，字段可能位于嵌套对象或列表中
     * 参数 fieldNames：需要加密的字段名集合；为空或对象为空时直接返回
     */
    void encryptFields(Object target, Collection<String> fieldNames);
}
