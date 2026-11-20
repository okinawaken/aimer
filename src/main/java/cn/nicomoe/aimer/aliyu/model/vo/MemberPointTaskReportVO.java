package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskReportRespDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 任务行为上报响应（{@code POST /aliyupoint/task/report}），对应整个响应体。 {@code success} 与 {@code code/msg} 位于顶层，不套用 {@link AliyuClientResult}。
 */
@Data
@AutoMapper(target = MemberPointTaskReportRespDTO.class)
public class MemberPointTaskReportVO {

    /**
     * 是否上报成功
     */
    private Boolean success;

    /**
     * 业务数据
     */
    private JsonNode data;
}
