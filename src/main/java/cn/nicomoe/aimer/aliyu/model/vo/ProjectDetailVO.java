package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ProjectDetailRespDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 项目详情（{@code GET /pro/project}）。
 */
@Data
@AutoMapper(target = ProjectDetailRespDTO.class)
public class ProjectDetailVO {

    /**
     * 项目 ID（订阅消息 businessId 使用）
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目海报
     */
    private String imgUrl;

    /**
     * 最低价（页面直接展示，未做数值运算）
     */
    private String minPrice;

    /**
     * 最低价说明（如“8 折起”）
     */
    private String lowestPriceDesc;

    /**
     * 售卖状态，{@code 3} 表示已结束
     */
    private Long saleState;

    /**
     * 坐席类型，{@code Number(seatType)} 后与 1/2 比较
     */
    private Long seatType;

    /**
     * 是否存在特权购买
     */
    private Boolean privilegeBuyState;

    /**
     * 特权购买说明：key 为特权描述，value 为该特权可购买的场次名称列表
     */
    private Map<String, List<String>> privilegeBuyMap;

    /**
     * 是否降级推荐（为 true 时不展示推荐位）
     */
    private Boolean grayRecommendDowngrade;

    /**
     * 倒计时状态
     */
    private Long countDownState;

    /**
     * 倒计时结束时间
     */
    private String countDownEndTime;

    /**
     * 项目开始时间（毫秒时间戳）
     */
    private Long startTime;

    /**
     * 项目结束时间（毫秒时间戳）
     */
    private Long endTime;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 场馆名称
     */
    private String siteName;

    /**
     * 是否麦秀项目
     */
    private Long isMx;

    /**
     * 是否开启微信订阅通知
     */
    private Boolean wxSubscribeNotifyEnabled;

    /**
     * 是否可以订阅微信通知
     */
    private Boolean wxSubscribeNotifyEnabledCanSubscribe;

    /**
     * 项目详情富文本（HTML）
     */
    private String projectDetail;

    /**
     * 活动时长类型：0/1/2/3/4，3 时展示 {@link #showTimeDesc}
     */
    private Long showTimeType;

    /**
     * 活动时长说明
     */
    private String showTimeDesc;

    /**
     * 入场时间类型：0/1/2/3，3 时展示 {@link #entryTimeDesc}
     */
    private Long entryTimeType;

    /**
     * 入场时间说明
     */
    private String entryTimeDesc;

    /**
     * 儿童票类型：0/1/2/3，3 时展示 {@link #childTicketDesc}
     */
    private Long childTicketType;

    /**
     * 儿童票说明
     */
    private String childTicketDesc;

    /**
     * 寄存说明类型：0/1，1 时展示 {@link #depositDesc}
     */
    private Long depositType;

    /**
     * 寄存说明
     */
    private String depositDesc;

    /**
     * 禁止携带物品说明
     */
    private String restrictedItem;

    /**
     * 退款规则
     */
    private RefundProjectRuleVO refundProjectRuleVo;

    /**
     * 项目级最大优惠信息，兼容单个对象与数组；元素结构见 {@link DiscountInfoVO}。
     */
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<DiscountInfoVO> detailMaxDiscountInfo;

    /**
     * 可领取优惠券列表
     */
    private List<DiscountInfoVO> discountInfo;

    /**
     * 场次列表
     */
    private List<ProjectEventVO> eventVoList;

    /**
     * 退款规则。{@code refundType} 为 {@code "1"} 时不支持退；为 {@code "5"} 时按手续费规则处理： {@code refundProcedureFeeRule} 为 {@code "1"} 使用项目级明细、{@code "2"/"4"} 使用场次规则、{@code "3"} 使用固定说明。
     */
    @Data
    public static class RefundProjectRuleVO {

        /**
         * 退款类型：{@code "1"} 不支持退，{@code "5"} 条件退
         */
        private String refundType;

        /**
         * 退票手续费规则：1 项目级统一规则、2/4 按场次规则、3 仅文案说明
         */
        private String refundProcedureFeeRule;

        /**
         * 项目级退票规则明细（{@code refundProcedureFeeRule === "1"} 时作为表格数据源）
         */
        private List<ProjectRefundRuleDetailVO> refundProjectRuleDetails;

        /**
         * 按场次下发的退票规则（{@code refundProcedureFeeRule === "2"/"4"} 时使用页签渲染）
         */
        private List<ProjectRefundEventRuleVO> refundEventRules;
    }

    /**
     * 退票规则表格行（项目级与场次级明细共用）。
     */
    @Data
    public static class ProjectRefundRuleDetailVO {

        /**
         * 申请退凭证时间段（表格列 {@code refundTimeContent}，直接展示）
         */
        private String refundTimeContent;

        /**
         * 退凭证手续费（表格列 {@code refundRuleContent}）
         */
        private String refundRuleContent;

        /**
         * 规则状态：0 不可用、1 当前阶段、其余普通展示
         */
        private Long refundRuleStatus;
    }

    /**
     * 场次级退票规则。
     */
    @Data
    public static class ProjectRefundEventRuleVO {

        /**
         * 场次 ID
         */
        private Long eventId;

        /**
         * 场次名称（页签文案）
         */
        private String eventName;

        /**
         * 该场次的退票手续费规则
         */
        private String refundProcedureFeeRule;

        /**
         * 该场次的退票规则明细
         */
        private List<ProjectRefundRuleDetailVO> refundEventRuleDetails;
    }

    /**
     * 优惠信息（项目级最大优惠、优惠券、用户优惠共用）。
     */
    @Data
    public static class DiscountInfoVO {

        /**
         * 优惠券 ID（领取时作为 ticketId 提交）
         */
        private String discountId;

        /**
         * 优惠券名称
         */
        private String discountName;

        /**
         * 优惠规则：{@code 1} 满减，其余按折扣展示
         */
        private Long discountRule;

        /**
         * 优惠有效期类型：{@code 2} 领取后固定天数
         */
        private Long discountTimeType;

        /**
         * 领取后有效天数
         */
        private Long discountFixDay;

        /**
         * 有效期开始时间（格式化后的日期）
         */
        private String discountStartTimeNew;

        /**
         * 有效期结束时间（格式化后的日期）
         */
        private String discountEndTimeNew;

        /**
         * 领取状态：0 未领取、1 已领取，其余按状态文案展示
         */
        private Long receiveStatus;

        /**
         * 优惠明细
         */
        private DiscountDetailVO discountDetail;
    }

    /**
     * 优惠明细。
     */
    @Data
    public static class DiscountDetailVO {

        /**
         * 优惠条件：1 满金额、2 满件数、3 指定票品
         */
        private List<Long> condition;

        /**
         * 优惠规则明细
         */
        private List<RuleDetailVO> ruleDetails;

        /**
         * 优惠对象（会员等级、渠道、操作员等）
         */
        private DiscountObjectsVO discountObjects;
    }

    /**
     * 优惠规则明细。
     */
    @Data
    public static class RuleDetailVO {

        /**
         * 满金额阈值（页面直接拼接文案）
         */
        private String ruleConditionTotalAmount;

        /**
         * 满件数阈值（页面直接拼接文案）
         */
        private String ruleConditionTotalCount;

        /**
         * 优惠值（满减金额或折扣）
         */
        private String ruleTypeValue;
    }

    /**
     * 优惠对象。
     */
    @Data
    public static class DiscountObjectsVO {

        /**
         * 会员限制
         */
        private MemberVO member;

        /**
         * 指定渠道
         */
        private List<String> markets;

        /**
         * 指定操作员角色
         */
        private List<String> operatorRoles;

        /**
         * 指定操作员权限
         */
        private List<String> operatorAuths;
    }

    /**
     * 会员限制。
     */
    @Data
    public static class MemberVO {

        /**
         * 会员等级名称
         */
        private List<String> memberLevelNames;

        /**
         * 会员类型名称
         */
        private List<String> memberTypeNames;
    }
}
