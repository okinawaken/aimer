package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 裂变抽奖助力请求（{@code POST /aliyu/act/registerActAssist}），需要 CSRF，错误由调用方处理。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityRegisterActAssistReqDTO {

    /**
     * 当前用户是否新用户（布尔）
     */
    private Boolean isNewUser;

    /**
     * 分享链接携带的邀请值（邀请码）
     */
    private String userShareLinkValue;
}