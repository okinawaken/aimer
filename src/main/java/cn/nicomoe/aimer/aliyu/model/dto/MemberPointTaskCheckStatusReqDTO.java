package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 任务状态检查请求（{@code POST /aliyupoint/task/checkStatus}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointTaskCheckStatusReqDTO {

    /** 任务 ID */
    private Long taskId;
}
