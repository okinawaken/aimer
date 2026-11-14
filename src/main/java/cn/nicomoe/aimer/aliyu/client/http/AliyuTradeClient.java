package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;

import java.util.Map;
import cn.nicomoe.aimer.aliyu.model.dto.resp.ConfirmGroupedCartRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.RenderConfigRespDTO;

/**
 * 交易 / 购物车域：{@code /trade}、{@code /shoppingCart}。下单页渲染、加购、确认、提交。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuTradeClient extends AliyuBaseClient {

    /**
     * 渲染配置（下单页）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/trade/renderConfig")
    AliyuClientResult<RenderConfigRespDTO> renderConfig(@JSONBody Map<String, Object> body);

    /**
     * 加购物车。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/trade/addCartGood")
    AliyuClientResult<Object> addCartGood(@JSONBody Map<String, Object> body);

    /**
     * 确认分组购物车。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/trade/confirmGroupedCart")
    AliyuClientResult<ConfirmGroupedCartRespDTO> confirmGroupedCart(@JSONBody Map<String, Object> body);

    /**
     * 提交分组购物车（下单，生成订单）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/trade/submitGroupedCart")
    AliyuClientResult<Object> submitGroupedCart(@JSONBody Map<String, Object> body);

    /**
     * 购物车加商品。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/shoppingCart/addCartGood")
    AliyuClientResult<Object> shoppingCartAddCartGood(@JSONBody Map<String, Object> body);

    /**
     * 校验商品在售。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/shoppingCart/checkGoodSale")
    AliyuClientResult<Object> checkGoodSale(@JSONBody Map<String, Object> body);
}
