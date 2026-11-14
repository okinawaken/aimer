package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 任务列表请求（{@code POST /aliyupoint/task/list}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointTaskListReqDTO {

    /** 页码，从 1 开始 */
    private Long page;

    /** 每页数量，默认 100 */
    private Long pageSize;
}
