package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ConfirmGroupedCartRespDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 确认分组购物车响应（{@code POST /trade/confirmGroupedCart}）。 金额为元字符串（如 {@code "0.00"}），需保留原始格式；时间为字符串。
 */
@Data
@AutoMapper(target = ConfirmGroupedCartRespDTO.class)
public class ConfirmGroupedCartVO {

    private List<JsonNode> allGoodVos;

    private Boolean asyncCreate;

    private String asyncTaskMsg;

    private JsonNode bearerVoPager;

    /**
     * 购物车商品（{@code onConfirm} 用于生成限购/票品限制、抖音预下单）
     */
    private List<CartGoodVO> cartGoods;

    /**
     * 商品展示卡片（下单确认页顶部商品信息）
     */
    private List<CartShowVO> cartShowVos;

    /**
     * 是否关闭「一票一证」
     */
    private Boolean closeOneTicketOneCard;

    /**
     * 是否关闭实名限购
     */
    private Boolean closeRealNameLimitBuy;

    /**
     * 手机号国际区号（联系方式展示）
     */
    private String countryAreaCode;

    /**
     * 套票商品卡片
     */
    private List<JsonNode> couponCartShowVos;

    /**
     * 套票优惠金额（「元」字符串）
     */
    private String couponDiscountMoney;

    /**
     * 证件类型候选列表，证件类型编码与 {@code itemValue} 文案复用 {@link CredentialListVO}。
     */
    private List<CredentialListVO> credentialList;

    private JsonNode customerAccount;

    private JsonNode customerIntegral;

    private String discountCode;

    /**
     * 运费（元字符串）
     */
    private String distributionMoney;

    /**
     * 邮箱脱敏提示文案
     */
    private String emailDesensitize;

    /**
     * 购物车是否为空（为空且无商品数据时跳回订单列表）
     */
    private Boolean emptyShoppingCarts;

    /**
     * 场次取票地址（按 {@code locationType} 过滤自提/快递地址）
     */
    private List<EventAddressVO> eventAddress;

    /**
     * 可用配送方式（元素为配送方式编码，如 2 快递、9 快递、10 自提）
     */
    private List<Long> expressTicket;

    /**
     * 首款金额（元字符串）
     */
    private String firstPayMoney;

    /**
     * 商品类型（1 金额商品 / 2 金额票 / 3 积分票）
     */
    private Long goodType;

    /**
     * 商品件数（限购/参与人数量校验用）
     */
    private Long goodsCount;

    /**
     * 自提二维码地址是否隐藏
     */
    private Boolean hideQRCodePickupAddress;

    private JsonNode hotProjectTip;

    private List<JsonNode> invalidCartShowVos;

    private List<JsonNode> invalidGoodVos;

    private List<JsonNode> invalidSingleCartShowVos;

    private Boolean isGaOrder;

    /**
     * 是否「票务通」链接订单（金额明细展示分支）
     */
    private Boolean isPwyLink;

    private String lastPayEndTime;

    /**
     * 尾款金额（元字符串）
     */
    private String lastPayMoney;

    private String lastPayStartTime;

    /**
     * 限购类型（0 不限 / 1 订单级限购 / 2 逐票限购）
     */
    private Long limitTotalType;

    private String midPayEndTime;

    /**
     * 中期款金额（元字符串）
     */
    private String midPayMoney;

    private String midPayStartTime;

    /**
     * 联系人姓名（需实名时作为默认值）
     */
    private String nameDesensitize;

    /**
     * 是否需要线上支付
     */
    private Boolean needOnlinePay;

    private Long nextQueryCount;

    private Long nextQueryTaskPeriod;

    private Long noticeType;

    private String notifyContent;

    /**
     * 线上支付金额（分，字符串，{@code onConfirm} 转为数字判断是否为 0 元单）
     */
    private String onlinePaymentFenMoney;

    /**
     * 线上支付金额（「元」字符串）
     */
    private String onlinePaymentMoney;

    private String orderDiscountId;

    /**
     * 订单优惠金额（「元」字符串）
     */
    private String orderDiscountMoney;

    /**
     * 可选订单优惠列表
     */
    private List<DiscountVO> orderDiscounts;

    private String orderFavorableMoney;

    /**
     * 订单商品件数
     */
    private Long orderGoodsCount;

    /**
     * 订单商品优惠金额（「元」字符串）
     */
    private String orderGoodsDiscountMoney;

    private String orderGoodsDiscountedMoney;

    /**
     * 商品总价（「元」字符串）
     */
    private String orderGoodsOriginalMoney;

    private String orderGoodsReceiveMoney;

    private String orderId;

    /**
     * 商品原价（「元」字符串，金额明细的原始金额）
     */
    private String orderOriginMoney;

    /**
     * 赠礼信息（{@code persentTypeName}/{@code persentContent}）
     */
    private List<OrderPersentVO> orderPersents;

    /**
     * 订单总金额（元字符串）
     */
    private String orderTotalMoney;

    private Long orderType;

    private String originFirstPayMoney;

    private String originLastPayMoney;

    private String originMidPayMoney;

    private Long payStep;

    /**
     * 选中的支付优惠 ID
     */
    private String paymentDiscountId;

    /**
     * 支付优惠金额（「元」字符串）
     */
    private String paymentDiscountMoney;

    private List<JsonNode> paymentDiscounts;

    /**
     * 手机号脱敏提示文案
     */
    private String phoneDesensitize;

    /**
     * 自提方式
     */
    private Long pickUpType;

    private Boolean pointFlag;

    private Long presaleRuleType;

    /**
     * 票品优惠金额（「元」字符串）
     */
    private String priceDiscountMoney;

    private String projectPtnrId;

    private String projectTenantId;

    private String promoCodeDiscountId;

    /**
     * 优惠码优惠金额（「元」字符串）
     */
    private String promoCodeDiscountMoney;

    /**
     * 可选优惠码列表
     */
    private List<DiscountVO> promoCodeDiscounts;

    private JsonNode promoCodeResult;

    /**
     * 专享优惠金额（「元」字符串）
     */
    private String promotionActivityMoney;

    private Long qrcodeType;

    /**
     * 应收金额（「元」字符串）
     */
    private String receivableMoney;

    private JsonNode refundProjectRuleVo;

    private JsonNode seatFreeCombine;

    private String serviceToken;

    /**
     * 确认页会话 ID
     */
    private String sessionId;

    private String shoppingCartToken;

    private Boolean showGegMemberShip;

    /**
     * 商品卡片（衍生确认页商品信息，逐项取项目名/图/单价/规格/数量）
     */
    private List<SingleCartShowVO> singleCartShowVos;

    private JsonNode studentDiscount;

    private List<JsonNode> surchargeMoneyVOS;

    /**
     * 第三方（文惠券）优惠
     */
    private ThirdDiscountVO thirdDiscountVo;

    private List<JsonNode> thirdMarkets;

    /**
     * 票卡标记（为真时配送方式 8 显示为 888）
     */
    private Boolean ticketCardFlag;

    /**
     * 可用凭证（配送）方式编码列表（如 9 快递、2 快递）
     */
    private List<Long> ticketCerts;

    private JsonNode ticketTypeTips;

    /**
     * 购物车 token（写入缓存 {@code tsic}，供 {@code /trade/submitGroupedCart} 使用）
     */
    private String token;

    private String totalAmount;

    private Long totalCount;

    /**
     * 共抵扣金额（「元」字符串）
     */
    private String totalDeduction;

    private String totalDerate;

    /**
     * 总优惠金额（「元」字符串）
     */
    private String totalDiscount;

    /**
     * 积分支付合计（积分票的应付积分，衍生确认页原样作为 {@code useDeductionPoint} 回传， 与 {@link #useDeductionPoint} 同为积分数量，故取 {@code Long}）
     */
    private Long totalIntegralAmount;

    /**
     * 商品积分合计
     */
    private String totalIntegrationAmount;

    /**
     * 商品合计金额（元字符串）
     */
    private String totalMoneyAmount;

    /**
     * 余额支付金额（「元」字符串）
     */
    private String useBalanceMoney;

    /**
     * 积分抵扣金额（「元」字符串）
     */
    private String useDeductionMoney;

    /**
     * 积分抵扣数量
     */
    private Long useDeductionPoint;

    /**
     * 选中的礼品卡 ID
     */
    private String useGiftCardId;

    /**
     * 礼品卡抵扣金额（「元」字符串）
     */
    private String useGiftCardMoney;

    private String useGiftCardNo;

    /**
     * 分销地址分页（默认地址用于自动选中收货地址）
     */
    private UserDistributionPagerVO userDistributionPager;

    private List<JsonNode> userGiftCardVos;

    /**
     * 用户 token（下单加密请求头 {@code userToken} 的取值）
     */
    private String userToken;

    /**
     * 购物车商品项。
     */
    @Data
    public static class CartGoodVO {

        /**
         * 项目 ID
         */
        private String projectId;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 项目图片
         */
        private String projectImgUrl;

        /**
         * 第三方商品 ID
         */
        private String thirdProductId;

        /**
         * 商品 ID（座次类型 1 使用）
         */
        private String goodId;

        /**
         * 价格 ID（座次类型 2 使用）
         */
        private String priceId;

        /**
         * 座次类型（1 按商品 / 2 按价格）
         */
        private Long seatType;

        /**
         * 子购物车商品（套票/组合票）
         */
        private List<CartGoodVO> childCartGoods;
    }

    /**
     * 商品展示卡片（下单确认页顶部）。
     */
    @Data
    public static class CartShowVO {

        /**
         * 场次 ID（列表 key）
         */
        private String eventId;

        /**
         * 项目 ID（埋点用）
         */
        private String projectId;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 项目图片
         */
        private String projectImgUrl;

        /**
         * 场次名称
         */
        private String eventName;

        /**
         * 场馆名称
         */
        private String siteName;

        /**
         * 座次类型（2 表示「无座」文案）
         */
        private Long seatType;

        /**
         * 单票价格展示项
         */
        private List<CartPriceShowVO> singleCartPriceShowVos;

        /**
         * 套票价格展示项
         */
        private List<CartPriceShowVO> couponCartPriceShowVos;
    }

    /**
     * 价格展示项。
     */
    @Data
    public static class CartPriceShowVO {

        /**
         * 价格 ID（列表 key）
         */
        private String priceId;

        /**
         * 价格名称
         */
        private String priceName;

        /**
         * 单价（「元」字符串）
         */
        private String price;

        /**
         * 实付单价（「元」字符串，优先于 {@code price} 展示）
         */
        private String realMoney;

        /**
         * 数量
         */
        private Long goodCount;

        /**
         * 凭证（座位）信息
         */
        private List<GoodVO> goodVos;

        /**
         * 子票价格展示项（套票）
         */
        private List<CartPriceShowVO> childPriceShowVos;
    }

    /**
     * 凭证（座位）信息。
     */
    @Data
    public static class GoodVO {

        /**
         * 商品 ID
         */
        private String goodId;

        /**
         * 座位 ID
         */
        private String seatId;

        /**
         * 楼层
         */
        private String floorName;

        /**
         * 区域
         */
        private String boundName;

        /**
         * 排号
         */
        private String rowName;

        /**
         * 座位号
         */
        private String seatName;
    }

    /**
     * 取票地址。
     */
    @Data
    public static class EventAddressVO {

        /**
         * 适用取票方式（2 快递 / 1 自提）
         */
        private List<Integer> locationType;

        /**
         * 地址文案
         */
        private String address;
    }

    /**
     * 优惠项（订单优惠与优惠码共用）。
     */
    @Data
    public static class DiscountVO {

        /**
         * 优惠 ID（优惠码列表也用它作为选中标识）
         */
        private String discountId;

        /**
         * 优惠名称
         */
        private String discountName;

        /**
         * 优惠金额（「元」字符串）
         */
        private String discountAmount;

        /**
         * 适用场次 ID 列表（逗号分隔）
         */
        private String orderDiscountEventIds;

        /**
         * 优惠规则 ID
         */
        private String orderDiscountRuleId;

        /**
         * 优惠码（仅优惠码列表项）
         */
        private String discountCode;
    }

    /**
     * 赠礼信息。
     */
    @Data
    public static class OrderPersentVO {

        /**
         * 赠礼类型名称
         */
        private String persentTypeName;

        /**
         * 赠礼内容文案
         */
        private String persentContent;
    }

    /**
     * 第三方优惠。
     */
    @Data
    public static class ThirdDiscountVO {

        /**
         * 优惠金额（「元」字符串）
         */
        private String discountMoney;
    }

    /**
     * 商品卡片（衍生确认页）。
     */
    @Data
    public static class SingleCartShowVO {

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 项目图片
         */
        private String projectImgUrl;

        /**
         * 金额商品单价（「元」字符串）
         */
        private String sourceMoney;

        /**
         * 积分商品所需积分（仅拼接展示）
         */
        private String pointAmount;

        /**
         * 规格组名称
         */
        private String modelType;

        /**
         * 规格名称
         */
        private String modelName;

        /**
         * 数量
         */
        private Long goodCount;
    }

    /**
     * 分销地址分页。
     */
    @Data
    public static class UserDistributionPagerVO {

        /**
         * 分销地址列表
         */
        private List<UserDistributionVO> dataList;

        /**
         * 当前页码
         */
        private Long page;

        /**
         * 每页数量
         */
        private Long pageSize;

        /**
         * 总页数
         */
        private Long totalPage;

        /**
         * 总条数
         */
        private Long totalRow;

        /**
         * 起始行
         */
        private Long startRow;

        /**
         * 结束行
         */
        private Long endRow;

        /**
         * 是否有下一页
         */
        private Boolean nextPage;
    }

    /**
     * 分销地址。
     */
    @Data
    public static class UserDistributionVO {

        /**
         * 地址 ID
         */
        private String disId;

        /**
         * 收件人姓名
         */
        private String receiverName;

        /**
         * 收件人电话
         */
        private String telPhone;

        /**
         * 是否默认地址（1 是 / 0 否）
         */
        private Long defaultDis;

        /**
         * 国家
         */
        private String countryName;

        /**
         * 省份
         */
        private String provinceName;

        /**
         * 城市
         */
        private String cityName;

        /**
         * 区县
         */
        private String areaName;

        /**
         * 详细地址
         */
        private String receiverAddress;
    }
}
