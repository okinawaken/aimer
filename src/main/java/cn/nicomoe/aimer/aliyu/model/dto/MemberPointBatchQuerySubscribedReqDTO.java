package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 批量查询微信订阅状态请求（{@code POST /aliyupoint/batchQuerySubscribed}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointBatchQuerySubscribedReqDTO {

    /**
     * 待查询的订阅项列表
     */
    private List<Item> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {

        /**
         * 微信订阅消息模板 ID
         */
        private String templateId;

        /**
         * 订阅类型（如 {@code BANNER_START}、{@code PROJECT_RESERVE}、{@code LOTTERY_RESERVE}）
         */
        private String subscribeType;

        /**
         * 业务 ID（项目/活动 ID）；{@code NEW_ARRIVAL} 类型时不携带
         */
        private Long businessId;

        /**
         * 关联的跳转链接，用于回填查询结果
         */
        private String jumpUrl;
    }
}
