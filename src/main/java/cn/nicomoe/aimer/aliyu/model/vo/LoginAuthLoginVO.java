package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.LoginAuthLoginRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 小程序授权登录响应（{@code POST /login/authLoginForApplet}）。
 */
@Data
@AutoMapper(target = LoginAuthLoginRespDTO.class)
public class LoginAuthLoginVO {

    /**
     * 登录流程状态码，{@code "23050016"}/{@code "23050017"} 表示需要继续处理
     */
    private String process;

    /**
     * 登录 token
     */
    private String authToken;

    /**
     * token 有效期毫秒数
     */
    private Long authTokenExpire;

    /**
     * 是否新注册用户
     */
    private Boolean isNewUser;

    /**
     * 登录用户信息
     */
    private LoginInfoVO loginInfo;

    /**
     * 登录用户信息（即写入本地 {@code userInfo} 的对象）。
     */
    @Data
    public static class LoginInfoVO {

        /**
         * 昵称
         */
        private String userName;

        /**
         * 用户 token
         */
        private String userToken;

        /**
         * 手机号
         */
        private String userTel;

        /**
         * 应用内用户 ID，用作微信订阅消息的 openId
         */
        private String appUid;
    }
}
