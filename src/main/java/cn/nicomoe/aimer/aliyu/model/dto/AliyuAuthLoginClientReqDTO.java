package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 小程序授权登录请求（{@code POST /login/authLoginForApplet}）。 支持 {@code authCode/authToken/appType/ext} 与 {@code jsCode/encryptedIv/encryptedData/userTel} 两类载荷； 前者的 {@code ext.iv}、{@code langType}、{@code skipAutoBind} 等字段按登录流程选填。 {@code authCode/jsCode} 为未加密的 {@code wx.login()} code； {@code encryptedData/iv} 来自真机 {@code wx.getPhoneNumber} 授权回调。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuAuthLoginClientReqDTO {

    /** 小程序平台类型，由小程序运行环境映射：抖音 {@code "1"}、支付宝 {@code "4"}、微信 {@code "5"}；简化授权载荷可不携带 */
    private String appType;

    /** {@code wx.login()} 返回的 code（非加密），形态 A 字段名；形态 B 中同值以 {@code jsCode} 传递 */
    private String authCode;

    /** mpassport token（{@code (0,h.bW)()} 产出） */
    private String authToken;

    /** 登录凭证包装：{@code iv} + {@code encryptedData} 来自 {@code wx.getPhoneNumber} 回调， {@code phone} / {@code messageCode} 为配套的手机号与短信验证码 */
    private Ext ext;

    /** 语言类型 */
    private Integer langType;

    /** 形态 B 专用：{@code wx.login()} 的 code，同 {@code authCode}。仅 jsCode 路径使用。 */
    private String jsCode;

    /** 形态 B 专用：与 {@code ext.iv} 同源，来自 {@code wx.getPhoneNumber} 回调的 {@code iv}。 */
    private String encryptedIv;

    /** 形态 B 专用：用户手机号（明文，作为服务端辅助校验）。 */
    private String userTel;

    /** 是否跳过自动绑定。 */
    private Boolean skipAutoBind;

    /** 邀请码。 */
    private String inviteCode;

    /** 阿里安全环境产出的验证码。 */
    private String sqmCode;

    /** 登录凭证包装对象（{@code ext}）。{@code iv/encryptedData} 来自 {@code wx.getPhoneNumber} 授权回调。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ext {

        /** AES 加密向量（微信 {@code getPhoneNumber} 回调 {@code iv}） */
        private String iv;

        /** 微信对手机号的加密包（微信平台产出，非本地加密） */
        private String encryptedData;

        /** 手机号（明文，配套校验） */
        private String phone;

        /** 短信验证码 */
        private String messageCode;
    }
}
