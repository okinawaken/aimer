package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 裂变抽奖活动详情响应（{@code GET /aliyu/act/fissionDrawActDetail}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FissionDrawActRespDTO {

    /**
     * 活动名称（分享标题、分享卡片文案）
     */
    private String actName;

    /**
     * 活动状态： 1 未开始、2 报名中、3 报名截止、4 待抽签、5 抽签中、6 中签、7 未中签、 8 超时未兑换、9 已下架。
     */
    private Long actStatus;

    /**
     * 活动状态描述文案
     */
    private String actStatusDesc;

    /**
     * 页面背景色（CSS 颜色值）
     */
    private String backgroundColor;

    /**
     * 报名开始时间（毫秒时间戳）
     */
    private String beginTime;

    /**
     * 抽签状态
     */
    private Long drawStatus;

    /**
     * 抽签时间（毫秒时间戳）
     */
    private String drawTime;

    /**
     * 报名结束时间（毫秒时间戳）
     */
    private String endTime;

    /**
     * 已助力人数（邀请记录标题「helpCount/inviteCodeLimit」）
     */
    private Long helpCount;

    /**
     * 邀请记录列表（空数组时展示空态）
     */
    private List<InvitationRecord> invitationRecords;

    /**
     * 邀请码上限，默认 5
     */
    private Long inviteCodeLimit;

    /**
     * 头图地址（分享图兜底）
     */
    private String mainPicUrl;

    /**
     * 我的抽签号码列表
     */
    private List<MyCode> myCodes;

    /**
     * 关联项目 ID（点击「前往预约」时拼项目详情路由）
     */
    private String projectId;

    /**
     * 参与数量（参与号码张数）
     */
    private Long registerCodeCount;

    /**
     * 活动规则页地址（点击规则图标跳转）
     */
    private String ruleUrl;

    /**
     * 分享图地址（分享卡片优先取该字段）
     */
    private String shareImageUrl;

    /**
     * 中签号码列表（元素直接作为文本渲染）
     */
    private List<String> winCodes;

    /**
     * 中签名额数（中签卡片右上角「N 个中签名额」）
     */
    private Long drawNum;

    /**
     * 底部按钮状态： 0 参与、1 邀请、2 预约。
     */
    private Long btnState;

    /**
     * 封面图地址（分享图 / 主图的兜底）
     */
    private String coverUrl;

    /**
     * 我的抽签号码（{@code myCodes} 元素）。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MyCode {

        /**
         * 是否中签（决定卡片样式与文案）
         */
        private Boolean win;

        /**
         * 抽签号码（直接作为文本渲染，可能为数字或字符串）
         */
        private String codeNumber;
    }

    /**
     * 邀请记录（{@code invitationRecords} 元素）。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InvitationRecord {

        /**
         * 助力状态：{@code "1"} 成功、{@code "2"} 已达上限
         */
        private String status;

        /**
         * 被邀请人昵称
         */
        private String inviteeName;

        /**
         * 助力时间
         */
        private String time;

        /**
         * 达到上限时合并展示的好友数量，默认 0
         */
        private Long total;
    }
}