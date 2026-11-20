package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskCheckStatusRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 任务状态检查响应（{@code POST /aliyupoint/task/checkStatus}）。
 */
@Data
@AutoMapper(target = MemberPointTaskCheckStatusRespDTO.class)
public class MemberPointTaskCheckStatusVO {

    /**
     * 任务 ID
     */
    private String taskId;

    /**
     * 任务状态：{@code -1} 删除、{@code 0} 下线、{@code 1} 上线
     */
    private Long status;

    /**
     * 任务状态描述
     */
    private String statusDesc;
}
