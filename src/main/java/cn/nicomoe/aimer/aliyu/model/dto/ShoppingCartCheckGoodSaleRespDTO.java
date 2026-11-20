package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 校验商品在售响应（{@code POST /shoppingCart/checkGoodSale}）。 响应中的 {@code token} 会写入缓存 {@code tcic}，供 {@code /trade/confirmGroupedCart} 使用。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartCheckGoodSaleRespDTO {

    /**
     * 确认购物车 token（有值时写入缓存 {@code tcic}，无值时清空）
     */
    private String token;
}
