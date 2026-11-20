package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 小鱼丸（积分）明细响应（{@code GET /aliyupoint/getPointsDetail}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsDetailRespDTO {

    /**
     * 积分变动记录列表
     */
    private java.util.List<Record> dataList;

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
     * 单条积分变动记录。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Record {

        /**
         * 门店名称（积分为线上兑换等场景时为空，渲染为 "-"）
         */
        private String storeName;

        /**
         * 变动类型编码（字符串）：如 {@code 50} 获得、{@code 100} 通用获得、 {@code 101} 通用扣减、{@code 1011} 商品兑换、{@code 1012} 积分兑换。
         */
        private String changeType;

        /**
         * 变动类型描述（优先展示，缺省时按 {@code changeType} 查前端枚举）
         */
        private String changeTypeDesc;

        /**
         * 积分变动数值（字符串，负数表示扣减）
         */
        private String pointsAmount;

        /**
         * 关联订单号（点击可复制）
         */
        private String orderNumber;

        /**
         * 交易时间（后端格式化字符串，直接展示）
         */
        private String transactionTime;

        /**
         * 记录唯一键（列表渲染的 React key，缺省时回落到下标）
         */
        private String uniqueKey;
    }
}