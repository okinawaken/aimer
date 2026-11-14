package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 任务状态检查响应（{@code POST /aliyupoint/task/checkStatus}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointTaskCheckStatusRespDTO {

    /** 任务 ID */
    private String taskId;

    /** 任务状态：{@code -1} 删除、{@code 0} 下线、{@code 1} 上线 */
    private Long status;

    /** 任务状态描述 */
    private String statusDesc;
}
