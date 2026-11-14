package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 成长值等级信息响应（{@code GET /aliyupoint/getGrowthValueTierInfo}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrowthValueTierRespDTO {

    /** 客户 ID */
    private String customerId;

    /** 当前成长值 */
    private String experience;

    /** 成长值规则简述（HTML 片段，会员等级页「小鱼丸说明」） */
    private String experienceRuleBrief;

    /** 成长值规则详情页地址（跳转 {@code growthValueRules}） */
    private String experienceUrl;

    /** 会员标识 */
    private Long memberFlag;

    /** 当前会员等级（缺省值 {@code "1"}，字符串语义，仅作展示/兜底） */
    private String memberLevel;

    /** 全部会员等级（按 {@code level} 升序渲染） */
    private java.util.List<MemberLevel> memberLevels;

    /** 昵称（缺省值空串，直接展示） */
    private String nickname;

    /** 会员等级项。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MemberLevel {

        /** 等级序号（参与排序与 {@code v{level}} 的等级 ID 生成） */
        private Long level;

        /** 等级编码（如 {@code LV1}） */
        private String levelCode;

        /** 等级名称（如「潜水鱼」） */
        private String levelName;

        /** 升级所需成长值（与当前 {@code experience} 做数值比较） */
        private Long upgradeThreshold;
    }
}