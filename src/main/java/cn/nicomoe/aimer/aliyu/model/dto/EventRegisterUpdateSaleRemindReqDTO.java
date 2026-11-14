package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 更新开售提醒请求（{@code POST /event/register/updateSaleRemind}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRegisterUpdateSaleRemindReqDTO {

    /** 项目 ID */
    private String projectId;

    /** 场次 ID（由场次 token 转数字） */
    private Long eventId;

    /** 提醒状态：1 开启，0 取消 */
    private Long remindState;
}