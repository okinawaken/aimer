package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 小程序授权登录响应（{@code POST /login/authLoginForApplet}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginAuthLoginRespDTO {

    /** 登录流程状态码，{@code "23050016"}/{@code "23050017"} 表示需要继续处理 */
    private String process;

    /** 登录 token */
    private String authToken;

    /** token 有效期毫秒数 */
    private Long authTokenExpire;

    /** 是否新注册用户 */
    private Boolean isNewUser;

    /** 登录用户信息 */
    private LoginInfo loginInfo;

    /** 登录用户信息（即写入本地 {@code userInfo} 的对象）。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoginInfo {

        /** 昵称 */
        private String userName;

        /** 用户 token */
        private String userToken;

        /** 手机号 */
        private String userTel;

        /** 应用内用户 ID，用作微信订阅消息的 openId */
        private String appUid;
    }
}
