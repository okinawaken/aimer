package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrowthValueTierRespDTO {
    private String customerId;
    private String experience;
    private String experienceRuleBrief;
    private String experienceUrl;
    private Long memberFlag;
    private Object memberLevel;
    private java.util.List<MemberLevel> memberLevels;
    private Object nickname;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MemberLevel {
        private Long level;
        private String levelCode;
        private String levelName;
        private Long upgradeThreshold;
    }
}
