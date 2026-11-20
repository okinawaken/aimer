package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 提交购物车下单请求（{@code POST /trade/submitGroupedCart}）。 {@code token} 由 {@code /trade/confirmGroupedCart} 下发； {@code receiveName/receiveCard/paymentPassword} 为 RSA 加密字段。 另一个 AWSC 令牌的协议键名尚未确认，未建模，需以真实请求核实。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeSubmitGroupedCartReqDTO {

    /**
     * 是否快捷下单
     */
    private Boolean quickTrade;

    /**
     * 收货信息参数
     */
    private TradeConfirmGroupedCartReqDTO.OrderReceiveParam orderReceiveParam;

    /**
     * 手动输入的优惠码
     */
    private String discountCode;

    /**
     * 优惠码规则 ID
     */
    private String discountCodeRuleId;

    /**
     * 使用的积分抵扣数量
     */
    private Long useDeductionPoint;

    /**
     * 使用的积分抵扣金额（「元」字符串）
     */
    private String useDeductionMoney;

    /**
     * 使用的礼品卡 ID
     */
    private String useGiftCardId;

    /**
     * 使用的礼品卡金额（「元」字符串）
     */
    private String useGiftCardMoney;

    /**
     * 订单级优惠 ID
     */
    private String orderDiscountId;

    /**
     * 订单级优惠规则 ID
     */
    private String orderDiscountRuleId;

    /**
     * 支付优惠 ID
     */
    private String paymentDiscountId;

    /**
     * 应收金额（「元」字符串）
     */
    private String receivableMoney;

    /**
     * 使用的余额金额（「元」字符串）
     */
    private String useBalanceMoney;

    /**
     * 确认页会话 ID（由 {@code /trade/confirmGroupedCart} 返回并回传）
     */
    private String sessionId;

    /**
     * 站点合伙人 ID（确认页取 {@code +siteShareInfo?.ptnrId}）
     */
    private Long ptnrId;

    /**
     * 小程序 UA 令牌（确认页取 {@code $app.awsc.getFYToken()}）
     */
    private String bxUA;

    /**
     * 校验商品清单（{@code formatEventId()} 产出）
     */
    private List<TradeConfirmGroupedCartReqDTO.CheckGood> checkGoods;

    /**
     * 限购限制（{@code limitTotalType === 1} 时传）
     */
    private OrderRestrict orderRestrict;

    /**
     * 票品限制（{@code limitTotalType === 2} 时传，按票品或价格 ID 逐项限制）
     */
    private List<TicketRestrict> ticketRestricts;

    /**
     * 支付密码（使用余额/礼品卡支付时必传，由 RSA 拦截器加密）
     */
    private String paymentPassword;

    /**
     * 商品清单，元素结构随商品类型变化。
     */
    private List<Object> shopGoods;

    /**
     * 配送方式（1 自提 / 2 快递）
     */
    private Integer ticketExpress;

    /**
     * 快递公司 ID（仅在 {@code ticketExpress === 2} 时传）
     */
    private String expressId;

    /**
     * 是否积分商品
     */
    private Boolean pointFlag;

    /**
     * 活动 ID（仅在 {@code bizType === 2} 时传）
     */
    private String activityId;

    /**
     * 是否部分提交
     */
    private Boolean partSubmit;

    /**
     * 下单令牌，由 {@code /trade/confirmGroupedCart} 响应写入（小程序 storage {@code tsic}）。
     */
    private String token;

    /**
     * 限购限制。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderRestrict {

        /**
         * 限购持有人 ID
         */
        private String bearerId;
    }

    /**
     * 票品限制。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TicketRestrict {

        /**
         * 商品 ID（与 {@code priceId} 二选一）
         */
        private String goodId;

        /**
         * 价格 ID（与 {@code goodId} 二选一）
         */
        private String priceId;

        /**
         * 持有人 ID
         */
        private String bearerId;
    }
}