package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 任务行为上报响应（{@code POST /aliyupoint/task/report}），对应整个响应体。 {@code success} 与 {@code code/msg} 位于顶层，不套用 {@link AliyuClientResult}。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointTaskReportRespDTO {

    /**
     * 是否上报成功
     */
    private Boolean success;

    /**
     * 业务数据
     */
    private JsonNode data;
}
