package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.MemberHomePageRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 会员首页信息响应（{@code GET /aliyupoint/getMemberHomePageInfo}）。
 */
@Data
@AutoMapper(target = MemberHomePageRespDTO.class)
public class MemberHomePageVO {

    /**
     * 用户名昵称
     */
    private String nickname;

    /**
     * 会员卡列表（会员首页「IP 粉丝卡」轮播）
     */
    private java.util.List<CartInfoVO> cartInfoList;

    /**
     * 会员等级标识（数值）
     */
    private Long memberFlag;

    /**
     * 当前会员等级（{@code memberFlag} 缺失时的兜底字段）
     */
    private Long memberLevel;

    /**
     * 当前等级序号（{@code memberFlag}、{@code memberLevel} 均缺失时的兜底字段）
     */
    private Long level;

    /**
     * 当前成长值
     */
    private String experience;

    /**
     * 全部会员等级（卡片内等级列表为空时兜底）
     */
    private java.util.List<MemberLevelVO> memberLevels;

    /**
     * 全部会员权益（等级内权益为空时兜底）
     */
    private java.util.List<CardBenefitVO> cardBenefitList;

    /**
     * 会员卡。
     */
    @Data
    public static class CartInfoVO {

        /**
         * 卡面背景图
         */
        private String cardBg;

        /**
         * 卡图标
         */
        private String cardIcon;

        /**
         * 会员卡 ID
         */
        private String cardId;

        /**
         * 会员卡名称
         */
        private String cardName;

        /**
         * 会员卡类型（积分明细 tab、积分规则查询的 cardType）
         */
        private String cardType;

        /**
         * 该卡对应的会员等级列表（优先于顶层 {@code memberLevels}）
         */
        private java.util.List<MemberLevelVO> memberLevels;

        /**
         * 该卡可用小鱼丸余额（字符串展示）
         */
        private String points;
    }

    /**
     * 会员等级。
     */
    @Data
    public static class MemberLevelVO {

        /**
         * 该等级包含的权益
         */
        private java.util.List<CardBenefitVO> cardBenefitList;

        /**
         * 等级内权益 ID 列表（判断权益是否命中本等级）
         */
        private java.util.List<String> cardBenefitIds;

        /**
         * 等级序号（参与排序与等级 ID 生成）
         */
        private Long level;

        /**
         * 等级编码（如 {@code LV1}）
         */
        private String levelCode;

        /**
         * 等级名称
         */
        private String levelName;

        /**
         * 升级所需成长值
         */
        private Long upgradeThreshold;
    }

    /**
     * 会员权益。
     */
    @Data
    public static class CardBenefitVO {

        /**
         * 权益 ID
         */
        private String id;

        /**
         * 权益 ID（{@code id} 缺失时兜底）
         */
        private String benefitId;

        /**
         * 权益规则 ID（{@code id/benefitId} 缺失时兜底）
         */
        private String ruleId;

        /**
         * 权益名称
         */
        private String ruleName;

        /**
         * 权益名称（{@code ruleName} 缺失时兜底）
         */
        private String benefitName;

        /**
         * 权益描述（优先展示，为空时回落到 {@code dispatchRule}）
         */
        private String benefitDescription;

        /**
         * 权益图标（{@code benefitIcon} 缺失时回落到 {@code ruleIcon}）
         */
        private String benefitIcon;

        /**
         * 权益图标（兜底）
         */
        private String ruleIcon;

        /**
         * 权益类型（如 {@code POINT_RATE}、{@code UPGRADE_POINT}、{@code FIXED_DISPLAY}）
         */
        private String benefitType;

        /**
         * 权益发放规则（字符串）
         */
        private String dispatchRule;

        /**
         * 是否为当前等级适用权益
         */
        private Boolean isApplicable;

        /**
         * 适用等级（逗号分隔的等级序号字符串）
         */
        private String applicableLevel;

        /**
         * 积分倍率文案
         */
        private String levelMultipliers;

        /**
         * 积分倍率（兜底）
         */
        private String pointRate;

        /**
         * 积分倍率文案（兜底）
         */
        private String rateText;

        /**
         * 积分倍率数值（兜底）
         */
        private String multiplier;
    }
}
