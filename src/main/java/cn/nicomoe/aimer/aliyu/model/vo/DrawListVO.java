package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.DrawListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 抽签记录列表响应（{@code POST /act/drawList}）。
 */
@Data
@AutoMapper(target = DrawListRespDTO.class)
public class DrawListVO {

    /**
     * 抽签记录列表
     */
    private List<DrawRecordVO> dataList;

    /**
     * 结束行号
     */
    private Long endRow;

    /**
     * 是否还有下一页
     */
    private Boolean nextPage;

    /**
     * 当前页码
     */
    private Long page;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 起始行号
     */
    private Long startRow;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总行数
     */
    private Long totalRow;

    /**
     * 抽签记录（{@code dataList} 元素）。
     */
    @Data
    public static class DrawRecordVO {

        /**
         * 活动 ID（列表 key，同时上报埋点 order_id）
         */
        private String actId;

        /**
         * 活动名称（卡片标题）
         */
        private String actName;

        /**
         * 活动封面图地址
         */
        private String coverUrl;

        /**
         * 场馆名称（卡片副标题）
         */
        private String venueName;

        /**
         * 用户抽签状态：按数字下标映射状态角标
         */
        private Long userDrawStatus;

        /**
         * 活动类型：等于 2 时点击直接跳抽签详情页，否则跳报名详情页
         */
        private Long actType;
    }
}
