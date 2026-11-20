package cn.nicomoe.aimer.aliyu.model.dto;

import lombok.Data;

/**
 * 发起支付请求（{@code POST /maipay2/doPay}）。
 */
@Data
public class CheckoutDoPayReqDTO {

    /**
     * 支付渠道 ID（取自 {@code renderPay} 返回的 {@code payTypeVOList[].payChannelId}）
     */
    private String payChannelId;

    /**
     * 外部支付单号（订单 ID）
     */
    private String outerPaymentNo;
}
