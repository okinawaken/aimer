package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperDressHomeRespDTO {
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
        private String ipCode;
        private Long materialCount;
        private java.util.List<Material> materials;
        private String seriesId;
        private String seriesName;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Material {
        private Long buttonStatus;
        private String buttonStatusText;
        private String materialId;
        private String materialName;
        private String previewUrl;
    }
}
