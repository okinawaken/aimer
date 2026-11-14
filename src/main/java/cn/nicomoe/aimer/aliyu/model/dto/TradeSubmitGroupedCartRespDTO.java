package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 提交购物车下单响应（{@code POST /trade/submitGroupedCart}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeSubmitGroupedCartRespDTO {

    /** 订单 ID（收银台 {@code outerPaymentNo} / 支付成功页参数） */
    private String orderId;
}