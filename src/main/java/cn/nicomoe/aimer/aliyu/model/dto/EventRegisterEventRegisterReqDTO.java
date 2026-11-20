package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 开售提醒登记请求（{@code POST /event/register/eventRegister}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRegisterEventRegisterReqDTO {

    /**
     * 场次 ID（由场次 token 转数字）
     */
    private Long eventId;
}