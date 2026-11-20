package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 积分规则（{@code GET /aliyupoint/getPointsRule}，响应为数组）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsRuleRespDTO {

    /**
     * 会员卡类型（用于按 cardType 匹配当前卡片的规则）
     */
    private String cardType;

    /**
     * 规则跳转地址（优先使用）
     */
    private String ruleUrl;

    /**
     * 规则 JSON 文本（{@code ruleUrl} 为空时解析其中的 {@code ruleUrl}）
     */
    private String ruleDetail;
}