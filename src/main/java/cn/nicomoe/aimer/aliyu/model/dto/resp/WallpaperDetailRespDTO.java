package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperDetailRespDTO {
    private Long buttonStatus;
    private String buttonStatusText;
    private Object exchangeLimit;
    private String materialId;
    private String materialName;
    private Long pointsPrice;
    private String pointsPrizeId;
    private String previewUrl;
    private Object prizeDescription;
    private Object remainStock;
    private Object seriesId;
    private Object seriesName;


}
