package cn.nicomoe.aimer.aliyu.model.dto;

import lombok.Data;

/** 发送邮箱验证码请求（{@code POST /msg/sendEmailMsg}）。 {@code email} 为 RSA 加密字段，字段名须保持一致。 */
@Data
public class MsgSendEmailMsgReqDTO {

    /** 阿里安全滑块签名参数 */
    private SignParam signParam;

    /** 邮箱（RSA 加密字段） */
    private String email;

    /** 是否支持邮箱校验 */
    private Boolean supportEmail;

    /** 验证码发送场景码：1 登录、2 注册、3 找回密码等 */
    private String sendCodeType;

    /** 阿里安全滑块签名参数 */
    @Data
    public static class SignParam {

        /** 场景标识 */
        private String scene;

        /** 签名 */
        private String sig;

        /** 会话 ID */
        private String sessionId;

        /** 令牌 */
        private String token;
    }
}
