package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 任务奖励领取请求（{@code POST /aliyupoint/task/claim}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointTaskClaimReqDTO {

    /**
     * 任务 ID
     */
    private Long taskId;
}
