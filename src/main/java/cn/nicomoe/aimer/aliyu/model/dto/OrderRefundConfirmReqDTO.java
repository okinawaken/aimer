package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 退款确认请求（{@code POST /order/orderRefundConfirm}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRefundConfirmReqDTO {

    /**
     * 订单号
     */
    private String orderToken;
}
