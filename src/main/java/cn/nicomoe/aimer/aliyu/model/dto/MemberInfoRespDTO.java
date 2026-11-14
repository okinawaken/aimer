package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 会员详情响应（{@code GET /aliyupoint/getMemberInfo}）。 响应可能额外包裹一层 {@code data}；字段结构与 {@link MemberHomePageRespDTO} 一致。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberInfoRespDTO {

    /** 用户名昵称 */
    private String nickname;

    /** 会员卡列表（每张卡带独立余额 {@code points}） */
    private java.util.List<CartInfo> cartInfoList;

    /** 客户 ID */
    private String customerId;

    /** 当前成长值 */
    private String experience;

    /** 成长值规则简述（HTML） */
    private String experienceRuleBrief;

    /** 成长值规则详情页地址 */
    private String experienceUrl;

    /** 会员等级标识（数值） */
    private Long memberFlag;

    /** 当前会员等级（{@code memberFlag} 缺失时的兜底字段） */
    private Long memberLevel;

    /** 当前等级序号（{@code memberFlag}、{@code memberLevel} 均缺失时的兜底字段） */
    private Long level;

    /** 全部会员等级（首页等级卡渲染用） */
    private java.util.List<MemberLevel> memberLevels;

    /** 全部会员权益（首页等级卡权益列表，等级内权益为空时兜底） */
    private java.util.List<CardBenefit> cardBenefitList;

    /** 会员卡。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CartInfo {

        /** 卡面背景图 */
        private String cardBg;

        /** 卡图标 */
        private String cardIcon;

        /** 会员卡 ID */
        private String cardId;

        /** 会员卡名称 */
        private String cardName;

        /** 会员卡类型（与 {@code getPointsRule}、{@code pointsDetail} 的 cardType 对应） */
        private String cardType;

        /** 该卡对应的会员等级列表（优先于顶层 {@code memberLevels}） */
        private java.util.List<MemberLevel> memberLevels;

        /** 该卡可用小鱼丸余额（字符串展示） */
        private String points;
    }

    /** 会员等级。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MemberLevel {

        /** 该等级包含的权益 */
        private java.util.List<CardBenefit> cardBenefitList;

        /** 等级内权益 ID 列表（用于按 {@code id/benefitId/ruleId} 判断权益是否命中本等级） */
        private java.util.List<String> cardBenefitIds;

        /** 等级序号（与 {@code experience} 阈值比较） */
        private Long level;

        /** 等级编码（如 {@code LV1}） */
        private String levelCode;

        /** 等级名称 */
        private String levelName;

        /** 升级所需成长值 */
        private Long upgradeThreshold;
    }

    /** 会员权益。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CardBenefit {

        /** 权益 ID */
        private String id;

        /** 权益 ID（{@code id} 缺失时兜底） */
        private String benefitId;

        /** 权益规则 ID（{@code id/benefitId} 缺失时兜底） */
        private String ruleId;

        /** 权益名称 */
        private String ruleName;

        /** 权益名称（{@code ruleName} 缺失时兜底） */
        private String benefitName;

        /** 权益描述（优先展示，为空时回落到 {@code dispatchRule}） */
        private String benefitDescription;

        /** 权益图标（{@code benefitIcon} 缺失时回落到 {@code ruleIcon}） */
        private String benefitIcon;

        /** 权益图标（兜底） */
        private String ruleIcon;

        /** 权益类型（如 {@code POINT_RATE}、{@code UPGRADE_POINT}、{@code FIXED_DISPLAY}） */
        private String benefitType;

        /** 权益发放规则，为字符串，部分场景使用 JSON 文本 */
        private String dispatchRule;

        /** 是否为当前等级适用权益（存在时优先于 {@code applicableLevel} 判断） */
        private Boolean isApplicable;

        /** 适用等级（逗号分隔的等级序号字符串，如 {@code "0,1,2"}） */
        private String applicableLevel;

        /** 积分倍率文案（如 {@code "1.5"}） */
        private String levelMultipliers;

        /** 积分倍率（{@code levelMultipliers} 缺失时兜底） */
        private String pointRate;

        /** 积分倍率文案（{@code pointRate} 缺失时兜底） */
        private String rateText;

        /** 积分倍率数值（{@code rateText} 缺失时兜底） */
        private String multiplier;
    }
}