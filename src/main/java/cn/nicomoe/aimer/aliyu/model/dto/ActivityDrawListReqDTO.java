package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 抽签记录列表请求（{@code POST /act/drawList}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDrawListReqDTO {

    /** 页码，从 1 开始 */
    private Long pageIndex;

    /** 每页数量，默认 10 */
    private Long pageSize;
}