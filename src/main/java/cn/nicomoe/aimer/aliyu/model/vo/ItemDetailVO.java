package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ItemDetailRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 商品详情（{@code GET /item/itemDetail}）。
 */
@Data
@AutoMapper(target = ItemDetailRespDTO.class)
public class ItemDetailVO {

    /**
     * 商品主图
     */
    private String imageUrl;

    /**
     * 商品名称（bizType=1）
     */
    private String itemName;

    /**
     * 活动名称（bizType=2）
     */
    private String activityName;

    /**
     * 价格（直接展示）
     */
    private String price;

    /**
     * 所需积分（页面与用户积分做 {@code +integral} 数值比较）
     */
    private Long integral;

    /**
     * 是否展示“xx 起”价格
     */
    private Boolean priceFlag;

    /**
     * 商品详情富文本（HTML）
     */
    private String detailInfo;

    /**
     * 兑换须知
     */
    private String activityDesc;

    /**
     * 是否有库存：0 库存不足、1 可兑换
     */
    private Long inventoryFlag;
}
