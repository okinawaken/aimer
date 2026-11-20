package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.OrderDetailRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单详情响应（{@code GET /order/queryOrderDetail}、{@code /order/queryOrderDetailNew}）。
 */
@Data
@AutoMapper(target = OrderDetailRespDTO.class)
public class OrderDetailVO {

    /**
     * 订单号
     */
    private String orderToken;

    /**
     * 下单时间
     */
    private String orderTime;

    /**
     * 支付时间（订单信息卡展示，缺省回退到 {@code orderTime}）
     */
    private String orderPayTime;

    /**
     * 支付方式名称（订单信息卡展示）
     */
    private String orderPayTypeName;

    /**
     * 订单状态（1 待支付 / 2 已支付 / 7 已完成 等）
     */
    private Long orderState;

    /**
     * 支付状态（1 未支付 / 2 已支付）
     */
    private Long orderPayState;

    /**
     * 退款状态（1 退款 / 2 部分退款 等）
     */
    private Long backOrderState;

    /**
     * 订单失效时间（用于支付倒计时）
     */
    private String orderFailureTime;

    /**
     * 服务端当前时间（用于支付倒计时）
     */
    private String systemTime;

    /**
     * 欠款金额（「元」，订单状态判断中与数字比较 {@code debtMoney > 0}）
     */
    private BigDecimal debtMoney;

    /**
     * 交易来源
     */
    private Long tradeFrom;

    /**
     * 删除状态（1 可删除）
     */
    private Long delState;

    /**
     * 是否允许申请退款
     */
    private Boolean refundFlag;

    /**
     * 是否满足退款条件
     */
    private Boolean refundConditionFlag;

    /**
     * 退款申请按钮状态
     */
    private Long refundApplyButtonStatus;

    /**
     * 电子票剩余次数
     */
    private Long eticketRemainingTimes;

    /**
     * 线上支付金额
     */
    private String onlinePaymentMoney;

    /**
     * 应收金额
     */
    private String receivableMoney;

    /**
     * 商品总金额
     */
    private String orderMoney;

    /**
     * 优惠券优惠金额
     */
    private String couponDiscountMoney;

    /**
     * 凭证券优惠金额
     */
    private String priceDiscountMoney;

    /**
     * 专享优惠金额
     */
    private String promotionDiscountMoney;

    /**
     * 订单优惠金额
     */
    private String orderDiscountMoney;

    /**
     * 优惠码优惠金额
     */
    private String promoCodeDiscountMoney;

    /**
     * 第三方优惠金额
     */
    private String thirdDiscountMoney;

    /**
     * 支付优惠金额
     */
    private String orderPaymentDiscountMoney;

    /**
     * 积分抵扣金额
     */
    private String useDeductionMoney;

    /**
     * 礼品卡抵扣金额
     */
    private String useGiftCardMoney;

    /**
     * 余额支付金额
     */
    private String useBalanceMoney;

    /**
     * 运费
     */
    private String distributionMoney;

    /**
     * 待支付金额（人民币）
     */
    private String receivePayMoneyRMB;

    /**
     * 商品类型
     */
    private Long goodType;

    /**
     * 当前渠道标记
     */
    private Boolean currentChannel;

    /**
     * 座次状态（1 无座 / 2 已出座 / 3 待出座 / 4 出座失败，待出座/出座失败时不展示「立即支付」）
     */
    private Long seatState;

    /**
     * 出票状态（2 已打印，已打印时不再展示电子票相关按钮）
     */
    private Long stockState;

    /**
     * 票种（0 未知 / 1 纸质 / 2 卡号 / 3 手机号 / 4 邮箱 / 7 纸质二维码 / 8 无纸化二维码 / 9 快递 / 10 自提）
     */
    private Long ticketType;

    /**
     * 是否已提交（仅 {@code orderType === 6}（支付链接订单）时判断落地页）
     */
    private Boolean linkSubmit;

    /**
     * 订单类型（6 为支付链接订单 {@code PAYMENT_LINK}）
     */
    private Long orderType;

    /**
     * 商品状态（1 待支付 / 7 已完成 等，{@code queryOrderDetailNew} 使用）
     */
    private Long itemState;

    /**
     * 商品状态说明
     */
    private String itemStateDesc;

    /**
     * 配送方式
     */
    private Long expressType;

    /**
     * 订单获得积分
     */
    private Long orderPoint;

    /**
     * 订单总金额
     */
    private String orderTotalMoney;

    /**
     * 收件 / 取票信息
     */
    private ReceiveInfoVO receiveInfoVo;

    /**
     * 支付明细列表
     */
    private List<OrderPayDetailVO> orderPayDetailList;

    /**
     * 赠礼信息（{@code persentTypeName} / {@code persentContent}，页面遍历拼接展示）
     */
    private List<ConfirmGroupedCartVO.OrderPersentVO> orderPersents;

    /**
     * 场次展示信息列表（轮播卡片）
     */
    private List<EventShowVO> orderEventShowVos;

    /**
     * 场次明细分摊行
     */
    private List<OrderDetailShowVO> singleOrderDetailShowBos;

    /**
     * 套票订单明细
     */
    private List<OrderDetailShowVO> orderCouponAllInfoLog;

    /**
     * 套票订单明细（{@code orderCouponAllInfoLog} 缺省时的回退字段）
     */
    private List<OrderDetailShowVO> couponOrderDetailShowBos;

    /**
     * 退款规则
     */
    private ProjectDetailVO.RefundProjectRuleVO refundProjectRuleVo;

    /**
     * 退款日志（{@code operateTypeName} / {@code logTime}）
     */
    private List<RefundLogVO> orderRefundLogs;

    /**
     * 商品明细列表（旧版 UI）
     */
    private List<OrderItemVO> orderItemVoList;

    /**
     * 收件 / 取票信息。
     */
    @Data
    public static class ReceiveInfoVO {

        /**
         * 快递单号
         */
        private String distributionNo;

        /**
         * 快递公司
         */
        private String expressCompany;

        /**
         * 票种（0 未知 / 1 纸质 / 2 卡号 / 3 手机号 / 4 邮箱 / 7 纸质二维码 / 8 无纸化二维码 / 9 快递 / 10 自提）
         */
        private Long ticketType;

        /**
         * 取票凭证地址列表（纯字符串）
         */
        private List<String> ticketAddressList;

        /**
         * 收件人姓名
         */
        private String receiveName;

        /**
         * 收件人电话
         */
        private String receivePhone;

        /**
         * 收件人邮箱（非快递票展示）
         */
        private String receiveEmail;

        /**
         * 证件号（卡号票 / 自提票展示）
         */
        private String receiveCard;

        /**
         * 证件类型（凭证信息卡展示，按枚举映射文案）
         */
        private String cardType;

        /**
         * 收货类型（1 自提 / 2 现场 / 3 快递，为 1 或 3 时才展示联系人区块）
         */
        private Long receiveType;

        /**
         * 运费支付方式（1 在线支付，其余运费到付）
         */
        private Long expressPayType;

        /**
         * 详细收货地址
         */
        private String receiveAddr;

        /**
         * 是否隐藏自提二维码地址
         */
        private Boolean hideQRCodePickupAddress;
    }

    /**
     * 支付明细。
     */
    @Data
    public static class OrderPayDetailVO {

        /**
         * 支付方式信息
         */
        private PayTypeVO payType;

        /**
         * 支付金额
         */
        private String payMoney;
    }

    /**
     * 支付方式。
     */
    @Data
    public static class PayTypeVO {

        /**
         * 支付渠道编码（优先作为文案 key）
         */
        private String paySupplierCode;

        /**
         * 支付方式名称
         */
        private String orderPayTypeName;
    }

    /**
     * 场次展示信息。
     */
    @Data
    public static class EventShowVO {

        /**
         * 场次 token
         */
        private String eventToken;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 项目图片
         */
        private String projectImgUrl;

        /**
         * 场馆名称
         */
        private String siteName;

        /**
         * 场次时间
         */
        private String showTime;

        /**
         * 场次变更原因
         */
        private String changeReason;

        /**
         * 场次变更标记（1 改期 / 2 取消）
         */
        private Long eventChangeTag;

        /**
         * 变更前开始时间
         */
        private String eventOriginalStartTime;

        /**
         * 变更前结束时间
         */
        private String eventOriginalEndTime;

        /**
         * 场次开始时间
         */
        private String eventTime;

        /**
         * 场次结束时间
         */
        private String eventEndTime;

        /**
         * 是否支持查看电子票夹
         */
        private Boolean mwlTicketFlag;

        /**
         * 票价列表
         */
        private List<EventPriceVO> priceList;

        /**
         * 普通票凭证列表
         */
        private List<SeatShowVO> singleSeatShowVos;

        /**
         * 套票凭证列表
         */
        private List<CouponSeatShowVO> couponSeatShowVos;

        /**
         * 普通票选座方式（传给座位卡片组件）
         */
        private Long normalSaleSeatSelectType;
    }

    /**
     * 凭证（座位）展示项。
     */
    @Data
    public static class SeatShowVO {

        /**
         * 单价
         */
        private String price;

        /**
         * 票价名称
         */
        private String priceName;

        /**
         * 座次类型
         */
        private Long seatType;

        /**
         * 座位 ID
         */
        private String seatId;

        /**
         * 区域 ID
         */
        private String boundId;

        /**
         * 座位状态
         */
        private Long seatState;

        /**
         * 退款进度日志
         */
        private List<RefundLogVO> orderLogVoS;

        /**
         * 票价列表（退款弹窗展示）
         */
        private List<EventPriceVO> priceList;

        /**
         * 最近一次退票快递费
         */
        private String latestRefundExpressMoney;

        /**
         * 退款金额
         */
        private String backMoney;

        /**
         * 观演人姓名
         */
        private String identityName;

        /**
         * 观演人证件号
         */
        private String identityCard;

        /**
         * 实名规则说明
         */
        private String identityRule;

        /**
         * 实名类型
         */
        private Long identityType;
    }

    /**
     * 套票凭证展示项。
     */
    @Data
    public static class CouponSeatShowVO {

        /**
         * 套票名称
         */
        private String priceName;

        /**
         * 套票单价
         */
        private String price;

        /**
         * 子凭证列表
         */
        private List<SeatShowVO> singleSeatShowVos;

        /**
         * 退款进度日志
         */
        private List<RefundLogVO> orderLogVoS;

        /**
         * 票价列表（退款弹窗展示）
         */
        private List<EventPriceVO> priceList;

        /**
         * 最近一次退票快递费
         */
        private String latestRefundExpressMoney;

        /**
         * 退款金额
         */
        private String backMoney;
    }

    /**
     * 场次票价。
     */
    @Data
    public static class EventPriceVO {

        /**
         * 票价 ID
         */
        private String priceId;

        /**
         * 票价名称
         */
        private String priceName;

        /**
         * 单价
         */
        private String price;

        /**
         * 数量
         */
        private Long priceCount;
    }

    /**
     * 退款日志。
     */
    @Data
    public static class RefundLogVO {

        /**
         * 操作类型名称
         */
        private String operateTypeName;

        /**
         * 操作时间
         */
        private String logTime;

        /**
         * 日志标题（退款进度时间线展示）
         */
        private String logTitle;

        /**
         * 日志编码（映射时间线文案 key，缺省时回退 {@code logTitle}）
         */
        private String logCode;
    }

    /**
     * 商品明细（旧版 UI 卡片）。
     */
    @Data
    public static class OrderItemVO {

        /**
         * 商品名称
         */
        private String itemName;

        /**
         * 商品图片
         */
        private String itemImgUrl;

        /**
         * 商品金额
         */
        private String itemMoney;

        /**
         * 规格名称
         */
        private String modelName;

        /**
         * 子规格名称
         */
        private String subModelName;

        /**
         * 数量
         */
        private Long count;
    }

    /**
     * 订单明细分摊行。
     */
    @Data
    public static class OrderDetailShowVO {

        /**
         * 场次 token（同场次明细以此归并）
         */
        private String eventToken;

        /**
         * 票价 token（优惠券明细与 {@code couponData} 匹配的键）
         */
        private String priceToken;

        /**
         * 票价别名
         */
        private String priceAlias;

        /**
         * 单价
         */
        private String price;

        /**
         * 座次类型
         */
        private Long seatType;

        /**
         * 楼层名称
         */
        private String floorName;

        /**
         * 区域名称
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

        /**
         * 项目 token
         */
        private String projectToken;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 项目图片
         */
        private String projectImgUrl;

        /**
         * 场馆名称
         */
        private String siteName;

        /**
         * 场次时间
         */
        private String showTime;

        /**
         * 场次变更原因
         */
        private String changeReason;

        /**
         * 场次变更标记
         */
        private Long eventChangeTag;

        /**
         * 变更前开始时间
         */
        private String eventOriginalStartTime;

        /**
         * 变更前结束时间
         */
        private String eventOriginalEndTime;

        /**
         * 场次开始时间
         */
        private String eventTime;

        /**
         * 场次结束时间
         */
        private String eventEndTime;

        /**
         * 是否支持查看电子票夹
         */
        private Boolean mwlTicketFlag;

        /**
         * 票价列表
         */
        private List<EventPriceVO> priceList;
    }
}
