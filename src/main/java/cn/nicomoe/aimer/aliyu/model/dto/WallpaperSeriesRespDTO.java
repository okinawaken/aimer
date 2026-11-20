package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 壁纸系列详情响应（{@code GET /aliyuMember/pointsMall/wallpaper/seriesDetail}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperSeriesRespDTO {

    /**
     * 系列 ID（数值）
     */
    private Long seriesId;

    /**
     * 系列名称
     */
    private String seriesName;

    /**
     * 系列内素材（壁纸）列表
     */
    private java.util.List<Material> materials;

    /**
     * 系列内单个素材。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Material {

        /**
         * 素材 ID（数值，下载接口入参）
         */
        private Long materialId;

        /**
         * 壁纸奖品 ID（数值，详情/兑换入参）
         */
        private Long pointsPrizeId;

        /**
         * 素材名称
         */
        private String materialName;

        /**
         * 预览图地址
         */
        private String previewUrl;

        /**
         * 兑换所需积分（数值）
         */
        private Long pointsPrice;

        /**
         * 按钮状态：{@code 1} 可兑换、{@code 99} 已获得、{@code 4} 积分不足等
         */
        private Long buttonStatus;

        /**
         * 按钮状态文案
         */
        private String buttonStatusText;
    }
}