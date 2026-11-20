package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 申请退款请求（{@code POST /order/applyRefund}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderApplyRefundReqDTO {

    /**
     * 订单号，字段名为 {@code orderId}，值取 {@code orderToken}
     */
    private String orderId;
}
