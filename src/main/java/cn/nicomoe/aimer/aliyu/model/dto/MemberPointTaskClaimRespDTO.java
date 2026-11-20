package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 任务领取响应（{@code POST /aliyupoint/task/claim}），对应整个响应体。 {@code success} 与 {@code code/msg} 位于顶层，不套用 {@link AliyuClientResult}； 业务码由调用方校验，不走响应拦截器的统一抛错。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointTaskClaimRespDTO {

    /**
     * 是否领取成功
     */
    private Boolean success;

    /**
     * 业务码（失败时如 {@code TASK_NOT_FOUND}、{@code TASK_ALREADY_CLAIMED}）
     */
    private String code;

    /**
     * 提示文案（失败场景）
     */
    private String msg;

    /**
     * 业务数据
     */
    private JsonNode data;
}
