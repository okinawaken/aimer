package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberInfoRespDTO {
    private java.util.List<CartInfo> cartInfoList;
    private String customerId;
    private String experience;
    private String experienceRuleBrief;
    private String experienceUrl;
    private Long memberFlag;
    private String nickname;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CartInfo {
        private String cardBg;
        private String cardIcon;
        private String cardId;
        private String cardName;
        private String cardType;
        private java.util.List<MemberLevel> memberLevels;
        private String points;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MemberLevel {
        private java.util.List<CardBenefit> cardBenefitList;
        private Long level;
        private String levelCode;
        private String levelName;
        private Long upgradeThreshold;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CardBenefit {
        private String benefitDescription;
        private String benefitIcon;
        private String benefitType;
        private Object dispatchRule;
        private String id;
        private Boolean isApplicable;
        private String levelMultipliers;
        private String ruleName;
    }
}
