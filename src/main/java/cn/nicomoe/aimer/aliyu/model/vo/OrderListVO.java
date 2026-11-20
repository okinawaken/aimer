package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.OrderListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单列表响应（{@code GET /order/queryOrderList}）。
 */
@Data
@AutoMapper(target = OrderListRespDTO.class)
public class OrderListVO {

    /**
     * 订单列表
     */
    private List<OrderListItemVO> dataList;

    /**
     * 结束行
     */
    private Long endRow;

    /**
     * 是否有下一页（分页控制）
     */
    private Boolean nextPage;

    /**
     * 当前页码
     */
    private Long page;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 起始行
     */
    private Long startRow;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总条数
     */
    private Long totalRow;

    /**
     * 订单列表项。
     */
    @Data
    public static class OrderListItemVO {

        /**
         * 订单号
         */
        private String orderToken;

        /**
         * 服务端当前时间（字符串，用于支付倒计时）
         */
        private String systemTime;

        /**
         * 订单失效时间（字符串，用于支付倒计时）
         */
        private String orderFailureTime;

        /**
         * 支付状态（1 已支付 / 2 未支付 / 3 部分支付）
         */
        private Long orderPayState;

        /**
         * 退款状态（1 部分退款 / 2 退款 / 3 无退款 / 4 待审核）
         */
        private Long backOrderState;

        /**
         * 订单状态（1 待支付 / 2 已完成 / 3 已取消 …）
         */
        private Long orderState;

        /**
         * 订单金额（「元」）
         */
        private BigDecimal orderMoney;

        /**
         * 运费（「元」，与 {@code orderMoney} 相加后展示合计）
         */
        private BigDecimal distributionMoney;

        /**
         * 交易来源（0 默认 / 1 开放平台 / 2 BBC）
         */
        private Long tradeFrom;

        /**
         * 欠款金额（「元」）
         */
        private BigDecimal debtMoney;

        /**
         * 场次卡片列表
         */
        private List<EventVO> orderEventVoList;

        /**
         * 电子票剩余次数
         */
        private Long eticketRemainingTimes;

        /**
         * 订单类型（6 为支付链接订单 {@code PAYMENT_LINK}）
         */
        private Long orderType;

        /**
         * 支付链接订单是否已提交（仅 {@code orderType === 6} 时判断）
         */
        private Boolean linkSubmit;

        /**
         * 座次状态（1 无座 / 2 已出座 / 3 待出座 / 4 出座失败，决定是否展示「立即支付」）
         */
        private Long seatState;
    }

    /**
     * 订单列表场次卡片。
     */
    @Data
    public static class EventVO {

        /**
         * 场次时间
         */
        private String eventTime;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 场馆名称
         */
        private String siteName;

        /**
         * 项目图片
         */
        private String projectImgUrl;

        /**
         * 场次变更标记
         */
        private Long eventChangeTag;

        /**
         * 场次变更原因
         */
        private String changeReason;
    }
}
