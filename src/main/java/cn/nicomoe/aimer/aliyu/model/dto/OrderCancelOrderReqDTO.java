package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 取消订单请求（{@code POST /order/cancelOrder}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCancelOrderReqDTO {

    /**
     * 订单号
     */
    private String orderToken;

    /**
     * 语言类型
     */
    private Integer langType;
}
