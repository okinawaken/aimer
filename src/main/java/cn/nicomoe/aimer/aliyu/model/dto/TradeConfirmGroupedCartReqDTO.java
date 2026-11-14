package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 确认购物车请求（{@code POST /trade/confirmGroupedCart}）。 使用 {@code isNewUI:true} 与缓存 {@code tcic} 的 token； 响应 {@link ConfirmGroupedCartRespDTO} 的 token 用于后续提交下单。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeConfirmGroupedCartReqDTO {

    /** 叠加的支付优惠 ID（确认页无可用优惠时传 {@code "-1"}） */
    private String paymentDiscountId;

    /** 是否快捷下单（确认页取 {@code params.quickTrade !== "false"}） */
    private Boolean quickTrade;

    /** 是否最优优惠（确认页取 {@code l2}，即用户是否勾选「使用最优优惠」） */
    private Boolean isOptimumDiscount;

    /** 渲染配置参数（确认页固定传值） */
    private RenderConfigParam renderConfigParam;

    /** 收货信息参数（仅当配送方式为快递且存在收货地址时传） */
    private OrderReceiveParam orderReceiveParam;

    /** 订单级优惠 ID */
    private String orderDiscountId;

    /** 订单级优惠活动 ID 列表（逗号分隔） */
    private String orderDiscountEventIds;

    /** 订单级优惠规则 ID */
    private String orderDiscountRuleId;

    /** 订单级优惠文案 */
    private String orderDiscountText;

    /** 手动输入的优惠码 */
    private String discountCode;

    /** 优惠码活动 ID 列表（逗号分隔） */
    private String discountCodeEventIds;

    /** 优惠码规则 ID */
    private String discountCodeRuleId;

    /** 优惠码文案 */
    private String discountCodeText;

    /** 校验商品清单（{@code formatEventId()} 产出，快捷下单或缺少活动 ID 时为空对象） */
    private List<CheckGood> checkGoods;

    /** 购物车 token（取自缓存 {@code tcic}，由 {@code /shoppingCart/checkGoodSale} 下发） */
    private String token;

    /** 是否新版 UI（action 内固定传 {@code true}） */
    private Boolean isNewUI;

    /** 商品清单，元素结构随商品类型变化。 */
    private List<Object> shopGoods;

    /** 是否积分商品（衍生页取 {@code 2 === +getStorageSync("curBizType")}） */
    private Boolean pointFlag;

    /** 快递公司 ID（衍生页仅在 {@code ticketExpress === 2} 时传） */
    private String expressId;

    /** 配送方式（1 自提 / 2 快递） */
    private Integer ticketExpress;

    /** 是否部分提交（衍生页取 {@code params.partSubmit === "true"}） */
    private Boolean partSubmit;

    /** 活动 ID（衍生页仅在 {@code bizType === 2} 时传） */
    private String activityId;

    /** 渲染配置参数。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RenderConfigParam {

        /** 是否脱敏展示手机号（确认页固定 {@code true}） */
        private Boolean phoneDesensitize;

        /** 是否需要凭证（确认页固定 {@code true}） */
        private Boolean credential;

        /** 是否需要分销地址（确认页固定 {@code true}） */
        private Boolean distribution;

        /** 分销地址页码（确认页固定 1） */
        private Integer distributionPage;

        /** 分销地址每页数量（确认页固定 20） */
        private Integer distributionPageSize;
    }

    /** 收货信息参数。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderReceiveParam {

        /** 运费（元字符串） */
        private String distributionMoney;

        /** 收货地址 ID（无地址时传 {@code "-1"}） */
        private String disId;

        /** 快递支付方式，固定 1 */
        private Integer expressPayType;

        /** 收货类型（对应配送方式枚举，如 9 快递、10 自提） */
        private Integer receiveType;

        /** 配送方式（同 {@code ticketType}，如 9 快递、10 自提） */
        private Integer ticketType;

        /** 收件人姓名（明文，提交时由 RSA 拦截器加密） */
        private String receiveName;

        /** 证件类型 */
        private String cardType;

        /** 证件号（明文，提交时由 RSA 拦截器加密） */
        private String receiveCard;
    }

    /** 校验商品项。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CheckGood {

        /** 活动 ID */
        private String eventId;
    }
}