package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 奖品详情响应（{@code GET /aliyuMember/pointsMall/prize/detail}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrizeDetailRespDTO {

    /**
     * 奖品 ID（数值）
     */
    private Long pointsPrizeId;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品图片
     */
    private String prizePic;

    /**
     * 兑换所需积分（数值）
     */
    private Long pointsPrice;

    /**
     * 剩余库存（数值）
     */
    private Long remainStock;

    /**
     * 兑换内容说明（富文本 HTML，详情页直接渲染）
     */
    private String prizeDescription;

    /**
     * 每人限兑数量（详情页「限兑N份」）
     */
    private Long exchangeLimit;

    /**
     * 可兑换状态：{@code 0} 可兑换，其它值表示不可兑换（前端据此置灰按钮并展示原因）。
     */
    private Long exchangeable;

    /**
     * 可兑换状态文案
     */
    private String exchangeableText;
}