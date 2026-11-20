package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 校验商品在售请求（{@code POST /shoppingCart/checkGoodSale}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartCheckGoodSaleReqDTO {

    /**
     * 是否积分商品
     */
    private Boolean pointFlag;

    /**
     * 商品 SKU 清单
     */
    private List<TradeAddCartGoodReqDTO.ShopGood> shopGoods;

    /**
     * 活动 ID（路由参数）
     */
    private String activityId;

    /**
     * 是否快捷下单，固定 {@code true}
     */
    private Boolean quickTrade;

    /**
     * 购物车 token（取自缓存 {@code tcis}，由 {@code /trade/addCartGood} 下发）
     */
    private String token;
}