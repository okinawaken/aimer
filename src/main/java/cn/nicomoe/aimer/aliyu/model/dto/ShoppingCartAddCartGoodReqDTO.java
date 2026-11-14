package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 购物车加商品请求（{@code POST /shoppingCart/addCartGood}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartAddCartGoodReqDTO {

    /** 是否积分商品 */
    private Boolean pointFlag;

    /** 商品 SKU 清单 */
    private List<TradeAddCartGoodReqDTO.ShopGood> shopGoods;

    /** 是否快捷下单（详情页固定 {@code false}） */
    private Boolean quickTrade;
}