package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 商品详情 / 商品购买查询请求（{@code GET /item/itemDetail}、{@code /item/itemBuy}）。 {@code bizType=2}（活动商品）时 {@code activityId} 必传。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemQueryReqDTO {

    /** 商品 ID。 */
    private String itemId;

    /** 活动 ID，仅活动类商品有值。 */
    private String activityId;

    /** 业务类型，路由参数 {@code bizType} 转数字。 */
    private Integer bizType;
}