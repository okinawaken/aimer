package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 壁纸兑换请求（{@code POST /aliyuMember/pointsMall/wallpaper/exchange}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsMallWallpaperExchangeReqDTO {

    /** 壁纸奖品 ID */
    private Long pointsPrizeId;

    /** 兑换数量 */
    private Integer amount;

    /** 门店 ID */
    private String storeId;
}
