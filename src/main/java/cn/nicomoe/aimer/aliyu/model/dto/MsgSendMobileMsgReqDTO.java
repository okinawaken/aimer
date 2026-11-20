package cn.nicomoe.aimer.aliyu.model.dto;

import lombok.Data;

/**
 * 发送手机短信验证码请求（{@code POST /msg/sendMobileMsg}）。 {@code telephone} 为 RSA 加密字段，字段名须保持一致。
 */
@Data
public class MsgSendMobileMsgReqDTO {

    /**
     * 阿里安全滑块签名参数
     */
    private SignParam signParam;

    /**
     * 手机号（RSA 加密字段）
     */
    private String telephone;

    /**
     * 验证码发送场景码：1 登录、2 注册、3 找回密码等
     */
    private String sendCodeType;

    /**
     * 手机号国家区号
     */
    private String countryAreaCode;

    /**
     * 阿里安全滑块签名参数
     */
    @Data
    public static class SignParam {

        /**
         * 场景标识
         */
        private String scene;

        /**
         * 签名
         */
        private String sig;

        /**
         * 会话 ID
         */
        private String sessionId;

        /**
         * 令牌
         */
        private String token;
    }
}
