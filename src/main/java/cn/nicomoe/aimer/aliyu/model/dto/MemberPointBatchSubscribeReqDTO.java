package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 批量订阅微信消息请求（{@code POST /aliyupoint/batchSubscribe}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointBatchSubscribeReqDTO {

    /**
     * 用户微信 openId
     */
    private String openId;

    /**
     * 订阅项列表
     */
    private List<Item> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {

        /**
         * 微信订阅消息模板 ID（由 {@code merchantTemplateConfig.templateItems} 按 subscribeType 匹配）
         */
        private String templateId;

        /**
         * 订阅类型（如 {@code BANNER_START}、{@code NEW_ARRIVAL}）
         */
        private String subscribeType;

        /**
         * 点击订阅消息后跳转的小程序页面路径
         */
        private String jumpUrl;

        /**
         * 活动名称（取自 CMS banner 的 {@code mainTitle}）
         */
        private String activityName;

        /**
         * 活动开始时间（取自 CMS banner 的 {@code releaseTime}）
         */
        private String startTime;

        /**
         * 开始前多少分钟提醒（取自 CMS banner 的 {@code reminderStopBeforeMinutes}）
         */
        private Integer stopBeforeStartMinutes;

        /**
         * 提醒文案（取自 CMS banner 的 {@code reminderTipContent}）
         */
        private String reminder;
    }
}
