package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 任务完成行为上报请求（{@code POST /aliyupoint/task/report}）。 使用 {@code Content-Type: application/x-www-form-urlencoded}； {@code eventType} 来自任务首个条件，{@code eventTag} 来自跳转链接的 {@code tc_stay} 参数。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointTaskReportReqDTO {

    /** 事件类型（如 {@code custom_event}） */
    private String eventType;

    /** 事件标签（jumpLink 中 {@code tc_stay} 参数的值，存在时才传） */
    private String eventTag;
}
