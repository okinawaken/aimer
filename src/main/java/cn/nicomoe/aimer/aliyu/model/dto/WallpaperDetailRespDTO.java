package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 壁纸详情响应（{@code GET /aliyuMember/pointsMall/wallpaper/detail}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperDetailRespDTO {

    /**
     * 壁纸奖品 ID（数值，详情查询与兑换入参）
     */
    private Long pointsPrizeId;

    /**
     * 素材 ID（数值）
     */
    private Long materialId;

    /**
     * 系列 ID（数值）
     */
    private Long seriesId;

    /**
     * 系列名称
     */
    private String seriesName;

    /**
     * 素材（壁纸）名称
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
     * 剩余库存（数值）
     */
    private Long remainStock;

    /**
     * 每人限兑数量（数值）
     */
    private Long exchangeLimit;

    /**
     * 兑换内容说明（富文本 HTML）
     */
    private String prizeDescription;

    /**
     * 按钮状态：{@code 1} 可兑换、{@code 99} 已获得、{@code 4} 积分不足等。
     */
    private Long buttonStatus;

    /**
     * 按钮状态文案（如「立即兑换」「已获得」）
     */
    private String buttonStatusText;
}