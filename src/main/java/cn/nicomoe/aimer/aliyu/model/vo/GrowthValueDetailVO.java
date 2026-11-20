package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.GrowthValueDetailRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 成长值（小鱼丸余额）明细响应（{@code GET /aliyupoint/getGrowthValueDetail}）。
 */
@Data
@AutoMapper(target = GrowthValueDetailRespDTO.class)
public class GrowthValueDetailVO {

    /**
     * 成长值变动记录列表
     */
    private java.util.List<RecordVO> dataList;

    /**
     * 结束行号（分页透传字段）
     */
    private Long endRow;

    /**
     * 当前页码
     */
    private Long page;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 起始行号（分页透传字段）
     */
    private Long startRow;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总记录数
     */
    private Long totalRow;

    /**
     * 单条成长值变动记录。
     */
    @Data
    public static class RecordVO {

        /**
         * 门店名称（无门店场景为空，渲染时整行隐藏）
         */
        private String storeName;

        /**
         * 业务类型编码（字符串）：{@code "1"} 线下消费、{@code "2"} 小鱼丸撤销（交易退款）、 {@code "-1"} 小鱼丸过期。
         */
        private String bizType;

        /**
         * 业务类型描述（优先展示，缺省时按 {@code bizType} 查前端枚举）
         */
        private String bizTypeDesc;

        /**
         * 成长值变动数值（字符串，负数表示扣减）
         */
        private String pointsAmount;

        /**
         * 关联订单号（存在时展示并可复制）
         */
        private String orderNumber;

        /**
         * 变动时间（后端格式化字符串，直接展示）
         */
        private String time;

        /**
         * 业务主键（列表渲染的 React key，缺省时回落到下标）
         */
        private String bizId;
    }
}
