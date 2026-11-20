package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 删除订单请求（{@code POST /order/delOrder}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDelOrderReqDTO {

    /**
     * 订单号
     */
    private String orderToken;

    /**
     * 语言类型
     */
    private Integer langType;
}
