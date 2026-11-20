package cn.nicomoe.aimer.aliyu.controller.client;

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
import cn.nicomoe.aimer.aliyu.model.vo.ConfirmGroupedCartVO;
import cn.nicomoe.aimer.aliyu.model.vo.RenderConfigVO;
import cn.nicomoe.aimer.aliyu.model.vo.ShoppingCartCheckGoodSaleVO;
import cn.nicomoe.aimer.aliyu.model.vo.TradeAddCartGoodVO;
import cn.nicomoe.aimer.aliyu.model.vo.TradeSubmitGroupedCartVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuTradeService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 阿里鱼交易 / 购物车域接口。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/trade")
public class AliyuTradeController {

    private final AliyuTradeService tradeService;

    /**
     * 渲染配置（下单页）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/render-config")
    public R<RenderConfigVO> renderConfig(@RequestBody TradeRenderConfigReqDTO form) {
        RenderConfigRespDTO renderConfigDTO = tradeService.renderConfig(form);
        RenderConfigVO renderConfigVO = MapstructUtils.convert(renderConfigDTO, RenderConfigVO.class);
        return R.ok(renderConfigVO);
    }

    /**
     * 加购物车。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cart/add-good")
    public R<TradeAddCartGoodVO> addCartGood(@RequestBody TradeAddCartGoodReqDTO form) {
        TradeAddCartGoodRespDTO addCartGoodDTO = tradeService.addCartGood(form);
        TradeAddCartGoodVO addCartGoodVO = MapstructUtils.convert(addCartGoodDTO, TradeAddCartGoodVO.class);
        return R.ok(addCartGoodVO);
    }

    /**
     * 确认分组购物车。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cart/confirm-grouped")
    public R<ConfirmGroupedCartVO> confirmGroupedCart(@RequestBody TradeConfirmGroupedCartReqDTO form) {
        ConfirmGroupedCartRespDTO confirmGroupedCartDTO = tradeService.confirmGroupedCart(form);
        ConfirmGroupedCartVO confirmGroupedCartVO = MapstructUtils.convert(confirmGroupedCartDTO, ConfirmGroupedCartVO.class);
        return R.ok(confirmGroupedCartVO);
    }

    /**
     * 提交分组购物车（下单，生成订单）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cart/submit-grouped")
    public R<TradeSubmitGroupedCartVO> submitGroupedCart(@RequestBody TradeSubmitGroupedCartReqDTO form) {
        TradeSubmitGroupedCartRespDTO submitGroupedCartDTO = tradeService.submitGroupedCart(form);
        TradeSubmitGroupedCartVO submitGroupedCartVO = MapstructUtils.convert(submitGroupedCartDTO, TradeSubmitGroupedCartVO.class);
        return R.ok(submitGroupedCartVO);
    }

    /**
     * 购物车加商品。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/shopping-cart/add-good")
    public R<Boolean> shoppingCartAddCartGood(@RequestBody ShoppingCartAddCartGoodReqDTO form) {
        return R.ok(tradeService.shoppingCartAddCartGood(form));
    }

    /**
     * 校验商品在售。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/shopping-cart/check-good-sale")
    public R<ShoppingCartCheckGoodSaleVO> checkGoodSale(@RequestBody ShoppingCartCheckGoodSaleReqDTO form) {
        ShoppingCartCheckGoodSaleRespDTO checkGoodSaleDTO = tradeService.checkGoodSale(form);
        ShoppingCartCheckGoodSaleVO checkGoodSaleVO = MapstructUtils.convert(checkGoodSaleDTO, ShoppingCartCheckGoodSaleVO.class);
        return R.ok(checkGoodSaleVO);
    }
}
