package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskListRespDTO {
    private java.util.List<DataObj> dataList;
    private Long endRow;
    private Long page;
    private Long pageSize;
    private Long startRow;
    private Long totalPage;
    private Long totalRow;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataObj {
        private Long applyStatus;
        private String applyStatusDesc;
        private java.util.List<Condition> conditionList;
        private Long distributionMode;
        private String distributionModeDesc;
        private String jumpLink;
        private Object progress;
        private String rewardDesc;
        private String taskId;
        private String taskName;
        private Long taskStatus;
        private String taskStatusDesc;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Condition {
        private Object eventTag;
        private String eventType;
    }
}
