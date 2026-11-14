package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 加入购物车请求（{@code POST /trade/addCartGood}）。 {@code token} 使用缓存 {@code tgca}；票品使用 {@code cartGoods}，商品 SKU 使用 {@code shopGoods}。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeAddCartGoodReqDTO {

    /** 活动 ID（SKU 页为选中场次的 {@code eventToken}；商品详情页为路由参数 {@code activityId}） */
    private String eventId;

    /** 购物车商品项（SKU 页按票品逐个提交） */
    private List<CartGood> cartGoods;

    /** 是否快捷下单，固定 {@code true} */
    private Boolean quickTrade;

    /** 是否积分商品（商品详情页取 {@code bizType === 2}） */
    private Boolean pointFlag;

    /** 商品 SKU 清单（商品详情页由 SKU 选择结果逐项补 {@code itemId} 后原样透传） */
    private List<ShopGood> shopGoods;

    /** 活动 ID（商品详情页路由参数） */
    private String activityId;

    /** 购物车 token（取自缓存 {@code tgca}，由 {@code /pro/event} 下发） */
    private String token;

    /** 购物车商品项。组合票使用 {@code combinePriceId/goodCount/childPriceIds}， 普通票使用 {@code priceId/goodCount}；数量默认字符串 {@code "1"}。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CartGood {

        /** 价格 ID（普通票） */
        private String priceId;

        /** 购买数量，默认字符串 {@code "1"} */
        private String goodCount;

        /** 组合票价格 ID（组合票，与 {@code priceId} 互斥） */
        private String combinePriceId;

        /** 子票价格 ID 列表（组合票） */
        private List<String> childPriceIds;
    }

    /** 商品 SKU 项，包含数量、规格、价格或积分及商品 ID。 {@code goodSku} 来自 {@code /item/itemBuy} 的 {@code modelSkuMap}，结构随商品类型变化。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShopGood {

        /** 商品 ID（详情页缓存 {@code curDetailItemId}） */
        private String itemId;

        /** 购买数量 */
        private Long goodCount;

        /** 规格 SKU 值 */
        private Object goodSku;

        /** 金额商品价格（「元」字符串，金额商品） */
        private String price;

        /** 积分商品所需积分（仅拼接展示，积分商品） */
        private String integral;
    }
}