package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperListRespDTO {
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
        private String coverUrl;
        private Long minPoints;
        private Object priority;
        private String seriesId;
        private String seriesName;
    }
}
