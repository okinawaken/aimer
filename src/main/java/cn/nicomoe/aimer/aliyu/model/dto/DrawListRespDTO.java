package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 抽签记录列表响应（{@code POST /act/drawList}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrawListRespDTO {

    /** 抽签记录列表 */
    private List<DrawRecord> dataList;

    /** 结束行号 */
    private Long endRow;

    /** 是否还有下一页 */
    private Boolean nextPage;

    /** 当前页码 */
    private Long page;

    /** 每页数量 */
    private Long pageSize;

    /** 起始行号 */
    private Long startRow;

    /** 总页数 */
    private Long totalPage;

    /** 总行数 */
    private Long totalRow;

    /** 抽签记录（{@code dataList} 元素）。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DrawRecord {

        /** 活动 ID（列表 key，同时上报埋点 order_id） */
        private String actId;

        /** 活动名称（卡片标题） */
        private String actName;

        /** 活动封面图地址 */
        private String coverUrl;

        /** 场馆名称（卡片副标题） */
        private String venueName;

        /** 用户抽签状态：按数字下标映射状态角标 */
        private Long userDrawStatus;

        /** 活动类型：等于 2 时点击直接跳抽签详情页，否则跳报名详情页 */
        private Long actType;
    }
}