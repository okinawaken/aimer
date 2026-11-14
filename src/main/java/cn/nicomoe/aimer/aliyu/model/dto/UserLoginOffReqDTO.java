package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 退出登录请求（{@code POST /user/loginOff}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginOffReqDTO {

    /** 登录 token */
    private String authToken;
}
