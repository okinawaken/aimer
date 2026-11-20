package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.TradeSubmitGroupedCartRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 提交购物车下单响应（{@code POST /trade/submitGroupedCart}）。
 */
@Data
@AutoMapper(target = TradeSubmitGroupedCartRespDTO.class)
public class TradeSubmitGroupedCartVO {

    /**
     * 订单 ID（收银台 {@code outerPaymentNo} / 支付成功页参数）
     */
    private String orderId;
}
