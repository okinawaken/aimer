package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuTradeClient;
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
import cn.nicomoe.aimer.aliyu.service.client.AliyuTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/** 阿里鱼交易 / 购物车域服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuTradeServiceImpl implements AliyuTradeService {

    private final AliyuTradeClient tradeClient;

    @Override
    public RenderConfigRespDTO renderConfig(TradeRenderConfigReqDTO form) {
        AliyuClientResult<RenderConfigRespDTO> result = tradeClient.renderConfig(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public TradeAddCartGoodRespDTO addCartGood(TradeAddCartGoodReqDTO form) {
        AliyuClientResult<TradeAddCartGoodRespDTO> result = tradeClient.addCartGood(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ConfirmGroupedCartRespDTO confirmGroupedCart(TradeConfirmGroupedCartReqDTO form) {
        AliyuClientResult<ConfirmGroupedCartRespDTO> result = tradeClient.confirmGroupedCart(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public TradeSubmitGroupedCartRespDTO submitGroupedCart(TradeSubmitGroupedCartReqDTO form) {
        AliyuClientResult<TradeSubmitGroupedCartRespDTO> result = tradeClient.submitGroupedCart(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object shoppingCartAddCartGood(ShoppingCartAddCartGoodReqDTO form) {
        AliyuClientResult<Object> result = tradeClient.shoppingCartAddCartGood(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object checkGoodSale(ShoppingCartCheckGoodSaleReqDTO form) {
        AliyuClientResult<Object> result = tradeClient.checkGoodSale(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
