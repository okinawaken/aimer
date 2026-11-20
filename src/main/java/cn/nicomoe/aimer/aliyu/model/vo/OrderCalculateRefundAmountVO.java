package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.OrderCalculateRefundAmountRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 计算退款金额响应（{@code GET /order/calculateRefundAmount?orderToken=}）。
 */
@Data
@AutoMapper(target = OrderCalculateRefundAmountRespDTO.class)
public class OrderCalculateRefundAmountVO {

    /**
     * 订单实付金额
     */
    private String orderPayMoney;

    /**
     * 不可退金额合计
     */
    private String refundNonMoney;

    /**
     * 实际可退金额
     */
    private String refundMoney;

    /**
     * 退票手续费
     */
    private String ticketRefundFeeMoney;

    /**
     * 手续费费率（文案 {$refundRuleFeeRate} 替换值）
     */
    private String refundRuleFeeRate;

    /**
     * 不可退金额明细
     */
    private List<RefundNonMoneyItemVO> refundNonMoneyList;

    /**
     * 不可退金额明细项。
     */
    @Data
    public static class RefundNonMoneyItemVO {

        /**
         * 不可退项名称（如服务费）
         */
        private String refundNonName;

        /**
         * 不可退金额
         */
        private String refundNonMoney;
    }
}
