package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 壁纸兑换校验响应（{@code GET /aliyuMember/pointsMall/wallpaper/check}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperCheckRespDTO {

    /** 壁纸奖品 ID（数值，缺省时回落到请求入参） */
    private Long pointsPrizeId;

    /** 奖品名称 */
    private String prizeName;

    /** 奖品图片 */
    private String prizePic;

    /** 兑换所需积分（数值） */
    private Long pointsPrice;

    /** 可兑换状态：{@code 0} 可兑换，非 0 为不可兑换原因码 */
    private Long exchangeable;

    /** 可兑换状态文案 */
    private String exchangeableText;
}