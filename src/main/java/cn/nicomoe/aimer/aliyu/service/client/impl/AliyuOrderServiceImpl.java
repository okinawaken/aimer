package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuOrderClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.OrderApplyRefundReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderCalculateRefundAmountRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderCancelOrderReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderDelOrderReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderRefundConfirmReqDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * 阿里鱼订单域服务实现。
 */
@Service
@RequiredArgsConstructor
public class AliyuOrderServiceImpl implements AliyuOrderService {

    private final AliyuOrderClient orderClient;

    @Override
    public OrderListRespDTO queryOrderList(Integer pageIndex, Integer pageSize, String orderListType, Boolean needCount) {
        AliyuClientResult<OrderListRespDTO> result = orderClient.queryOrderList(pageIndex, pageSize, orderListType, needCount);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public OrderDetailRespDTO queryOrderDetail(String orderToken, Boolean isNewUI) {
        AliyuClientResult<OrderDetailRespDTO> result = orderClient.queryOrderDetail(orderToken, isNewUI);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public OrderDetailRespDTO queryOrderDetailNew(String orderToken) {
        AliyuClientResult<OrderDetailRespDTO> result = orderClient.queryOrderDetailNew(orderToken);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public OrderCalculateRefundAmountRespDTO calculateRefundAmount(String orderToken) {
        AliyuClientResult<OrderCalculateRefundAmountRespDTO> result = orderClient.calculateRefundAmount(orderToken);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean ticketDiscountReceive(String ticketId) {
        AliyuClientResult<Boolean> result = orderClient.ticketDiscountReceive(ticketId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean cancelOrder(OrderCancelOrderReqDTO form) {
        AliyuClientResult<Boolean> result = orderClient.cancelOrder(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean delOrder(OrderDelOrderReqDTO form) {
        AliyuClientResult<Boolean> result = orderClient.delOrder(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean applyRefund(OrderApplyRefundReqDTO form) {
        AliyuClientResult<Boolean> result = orderClient.applyRefund(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean orderRefundConfirm(OrderRefundConfirmReqDTO form) {
        AliyuClientResult<Boolean> result = orderClient.orderRefundConfirm(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean restTickets(Map<String, Object> form) {
        AliyuClientResult<Boolean> result = orderClient.restTickets(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
