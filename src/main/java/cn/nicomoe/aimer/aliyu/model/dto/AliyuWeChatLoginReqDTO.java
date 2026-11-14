package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/** 微信旧版授权登录载荷（{@code POST /login/authLogin}）。 <p> 字段命名沿用小程序“形态 B”载荷。{@code jsCode} 取自 {@code wx.login()}， {@code encryptedData} / {@code encryptedIv} 来自 {@code wx.getPhoneNumber} 授权回调，均由微信客户端产出； {@code sqmCode} 来自阿里安全环境，缺失时无法由服务端补算。为空的可选字段不参与序列化。 */
@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AliyuWeChatLoginReqDTO {

    /** 语言类型（简体 1） */
    private Integer langType = 1;

    /** {@code wx.login()} 返回的 code（非加密） */
    private String jsCode;

    /** 微信授权回调的加密向量 */
    private String encryptedIv;

    /** 微信对手机号的加密包 */
    private String encryptedData;

    /** 用户手机号（明文，服务端辅助校验） */
    private String userTel;

    /** 阿里安全环境产出的验证码，可选 */
    private String sqmCode;
}
