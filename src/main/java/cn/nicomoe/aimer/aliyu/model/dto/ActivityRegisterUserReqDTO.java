package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 活动报名请求（{@code POST /act/registerUser}）。 接受微信订阅消息后携带 {@code openId/templateId}；响应 {@code data} 为布尔，{@code success/data} 均为 true 表示成功。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityRegisterUserReqDTO {

    /** 活动 ID */
    private String actId;

    /** 实名参与人 ID */
    private String bearerId;

    /** 报名成功后的回跳页面 */
    private String page;

    /** 微信 openId（订阅消息场景，取本地 {@code userInfo.appUid}） */
    private String openId;

    /** 微信订阅消息模板 ID */
    private String templateId;
}