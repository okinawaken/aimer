package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 成长任务列表响应（{@code POST /aliyupoint/task/list}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskListRespDTO {

    /**
     * 任务列表
     */
    private java.util.List<DataObj> dataList;

    /**
     * 结束行号
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
     * 起始行号
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
     * 单个成长任务。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataObj {

        /**
         * 领取状态：{@code 1} 去完成、{@code 2} 可领取、{@code 3} 已完成
         */
        private Long applyStatus;

        /**
         * 领取状态按钮文案（直接展示）
         */
        private String applyStatusDesc;

        /**
         * 任务条件列表，上报使用首项的 {@code eventType}
         */
        private java.util.List<Condition> conditionList;

        /**
         * 发放模式
         */
        private Long distributionMode;

        /**
         * 发放模式描述
         */
        private String distributionModeDesc;

        /**
         * 跳转链接（「去完成」时 {@code navigateTo} 该链接，并从其查询参数取 {@code tc_stay}）
         */
        private String jumpLink;

        /**
         * 任务进度（存在时渲染 {@code completedCount/totalCount}）
         */
        private Progress progress;

        /**
         * 奖励描述（非空时展示奖励标签）
         */
        private String rewardDesc;

        /**
         * 任务 ID（作为 {@code task/checkStatus}、{@code task/claim} 的入参）
         */
        private String taskId;

        /**
         * 任务名称
         */
        private String taskName;

        /**
         * 任务状态：-1 已删除、0 下线、1 上线
         */
        private Long taskStatus;

        /**
         * 任务状态描述
         */
        private String taskStatusDesc;
    }

    /**
     * 任务进度。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Progress {

        /**
         * 已完成次数（与 {@code totalCount} 做数值比较）
         */
        private Long completedCount;

        /**
         * 需完成总次数
         */
        private Long totalCount;
    }

    /**
     * 任务条件。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Condition {

        /**
         * 事件标签。上报接口的 {@code eventTag} 来自跳转链接的 {@code tc_stay} 参数，并非此字段。
         */
        private String eventTag;

        /**
         * 事件类型（作为 {@code task/report} 的 {@code eventType} 入参）
         */
        private String eventType;
    }
}
