package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 会员装扮（壁纸装扮首页）响应（{@code GET /aliyuMember/pointsMall/wallpaper/dressHome}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperDressHomeRespDTO {

    /** 系列列表 */
    private java.util.List<DataObj> dataList;

    /** 当前页码 */
    private Long pageNo;

    /** 每页数量 */
    private Long pageSize;

    /** 总页数（用于判断是否继续加载） */
    private Long totalPage;

    /** 总记录数 */
    private Long totalRow;

    /** 装扮系列。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataObj {

        /** IP 编码 */
        private String ipCode;

        /** 系列内素材数量（数值） */
        private Long materialCount;

        /** 系列 ID（数值） */
        private Long seriesId;

        /** 系列名称 */
        private String seriesName;

        /** 系列内素材列表 */
        private java.util.List<Material> materials;
    }

    /** 系列内单个素材。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Material {

        /** 素材 ID（数值） */
        private Long materialId;

        /** 素材名称 */
        private String materialName;

        /** 预览图地址 */
        private String previewUrl;

        /** 按钮状态：{@code 1} 可兑换、{@code 99} 已获得等 */
        private Long buttonStatus;

        /** 按钮状态文案 */
        private String buttonStatusText;
    }
}