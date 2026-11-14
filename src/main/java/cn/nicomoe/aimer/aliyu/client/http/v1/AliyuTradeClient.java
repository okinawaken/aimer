package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.ConfirmGroupedCartRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RenderConfigRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShoppingCartAddCartGoodReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShoppingCartCheckGoodSaleReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeAddCartGoodReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeAddCartGoodRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeConfirmGroupedCartReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeRenderConfigReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeSubmitGroupedCartReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TradeSubmitGroupedCartRespDTO;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;

/** 交易 / 购物车域。下单页渲染、加购、确认、提交。 */
public interface AliyuTradeClient extends AliyuBaseClient {

    /** 渲染配置（下单页）。取协议条款与支付网关列表。 */
    @Post(url = "/trade/renderConfig")
    AliyuClientResult<RenderConfigRespDTO> renderConfig(@JSONBody TradeRenderConfigReqDTO body);

    /** 加购物车（SKU 下单 / 商品详情两处共用）。响应 {@code token} 需写入缓存 {@code tcis}。 */
    @Post(url = "/trade/addCartGood")
    AliyuClientResult<TradeAddCartGoodRespDTO> addCartGood(@JSONBody TradeAddCartGoodReqDTO body);

    /** 确认分组购物车。响应 {@code token} 需写入缓存 {@code tsic}，供 {@link #submitGroupedCart} 使用。 */
    @Post(url = "/trade/confirmGroupedCart")
    AliyuClientResult<ConfirmGroupedCartRespDTO> confirmGroupedCart(@JSONBody TradeConfirmGroupedCartReqDTO body);

    /** 提交分组购物车（下单，生成订单）。 */
    @Post(url = "/trade/submitGroupedCart")
    AliyuClientResult<TradeSubmitGroupedCartRespDTO> submitGroupedCart(@JSONBody TradeSubmitGroupedCartReqDTO body);

    /** 购物车加商品，响应 {@code token} 用作后续在售校验的请求凭证。 */
    @Post(url = "/shoppingCart/addCartGood")
    AliyuClientResult<Object> shoppingCartAddCartGood(@JSONBody ShoppingCartAddCartGoodReqDTO body);

    /** 校验商品在售，请求 {@code token} 来自购物车加商品的响应。 */
    @Post(url = "/shoppingCart/checkGoodSale")
    AliyuClientResult<Object> checkGoodSale(@JSONBody ShoppingCartCheckGoodSaleReqDTO body);
}