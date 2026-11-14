package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperExchangeRespDTO {
    private Long exchangeable;
    private String exchangeableText;
    private Object materialId;
    private Object pointsPrice;
    private Object pointsPrizeId;
    private Object prizeName;
    private Object prizePic;
    private Object seriesId;


}
