package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.WallpaperDressHomeRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 会员装扮（壁纸装扮首页）响应（{@code GET /aliyuMember/pointsMall/wallpaper/dressHome}）。
 */
@Data
@AutoMapper(target = WallpaperDressHomeRespDTO.class)
public class WallpaperDressHomeVO {

    /**
     * 系列列表
     */
    private java.util.List<DataObjVO> dataList;

    /**
     * 当前页码
     */
    private Long pageNo;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 总页数（用于判断是否继续加载）
     */
    private Long totalPage;

    /**
     * 总记录数
     */
    private Long totalRow;

    /**
     * 装扮系列。
     */
    @Data
    public static class DataObjVO {

        /**
         * IP 编码
         */
        private String ipCode;

        /**
         * 系列内素材数量（数值）
         */
        private Long materialCount;

        /**
         * 系列 ID（数值）
         */
        private Long seriesId;

        /**
         * 系列名称
         */
        private String seriesName;

        /**
         * 系列内素材列表
         */
        private java.util.List<MaterialVO> materials;
    }

    /**
     * 系列内单个素材。
     */
    @Data
    public static class MaterialVO {

        /**
         * 素材 ID（数值）
         */
        private Long materialId;

        /**
         * 素材名称
         */
        private String materialName;

        /**
         * 预览图地址
         */
        private String previewUrl;

        /**
         * 按钮状态：{@code 1} 可兑换、{@code 99} 已获得等
         */
        private Long buttonStatus;

        /**
         * 按钮状态文案
         */
        private String buttonStatusText;
    }
}
