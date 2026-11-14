package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 壁纸兑换响应（{@code POST /aliyuMember/pointsMall/wallpaper/exchange}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperExchangeRespDTO {

    /** 可兑换状态：{@code 0} 成功、{@code -1} 失败、其它值表示仍有未满足条件 */
    private Long exchangeable;

    /** 可兑换状态文案 */
    private String exchangeableText;

    /** 壁纸奖品 ID（数值） */
    private Long pointsPrizeId;

    /** 系列 ID（数值，兑换成功后跳转壁纸详情） */
    private Long seriesId;

    /** 素材 ID（数值，兑换成功后跳转壁纸详情） */
    private Long materialId;

    /** 奖品名称 */
    private String prizeName;

    /** 奖品图片 */
    private String prizePic;

    /** 兑换消耗积分（数值） */
    private Long pointsPrice;
}