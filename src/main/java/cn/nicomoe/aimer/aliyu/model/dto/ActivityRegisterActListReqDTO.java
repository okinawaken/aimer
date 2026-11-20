package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 活动抽签列表请求（{@code POST /act/registerActList}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityRegisterActListReqDTO {

    /**
     * 页码，从 1 开始
     */
    private Long pageIndex;

    /**
     * 每页数量，默认 10
     */
    private Long pageSize;
}