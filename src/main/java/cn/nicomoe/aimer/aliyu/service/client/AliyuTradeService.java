package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.ConfirmGroupedCartRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RenderConfigRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShoppingCartAddCartGoodReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShoppingCartCheckGoodSaleReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShoppingCartCheckGoodSaleRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeAddCartGoodReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeAddCartGoodRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeConfirmGroupedCartReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeRenderConfigReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeSubmitGroupedCartReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeSubmitGroupedCartRespDTO;

/**
 * 阿里鱼交易 / 购物车域服务，返回已解包的业务数据。
 */
public interface AliyuTradeService {

    /**
     * 渲染配置（下单页）。
     */
    RenderConfigRespDTO renderConfig(TradeRenderConfigReqDTO form);

    /**
     * 加购物车。
     */
    TradeAddCartGoodRespDTO addCartGood(TradeAddCartGoodReqDTO form);

    /**
     * 确认分组购物车。
     */
    ConfirmGroupedCartRespDTO confirmGroupedCart(TradeConfirmGroupedCartReqDTO form);

    /**
     * 提交分组购物车（下单，生成订单）。
     */
    TradeSubmitGroupedCartRespDTO submitGroupedCart(TradeSubmitGroupedCartReqDTO form);

    /**
     * 购物车加商品。
     */
    Boolean shoppingCartAddCartGood(ShoppingCartAddCartGoodReqDTO form);

    /**
     * 校验商品在售。
     */
    ShoppingCartCheckGoodSaleRespDTO checkGoodSale(ShoppingCartCheckGoodSaleReqDTO form);
}
