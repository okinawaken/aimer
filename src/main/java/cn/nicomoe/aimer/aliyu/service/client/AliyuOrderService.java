package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.OrderApplyRefundReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderCalculateRefundAmountRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderCancelOrderReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderDelOrderReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderRefundConfirmReqDTO;

import java.util.Map;

/**
 * 阿里鱼订单域服务，返回已解包的业务数据。
 */
public interface AliyuOrderService {

    /**
     * 订单列表。
     */
    OrderListRespDTO queryOrderList(Integer pageIndex, Integer pageSize, String orderListType, Boolean needCount);

    /**
     * 订单详情。
     */
    OrderDetailRespDTO queryOrderDetail(String orderToken, Boolean isNewUI);

    /**
     * 订单详情（新版 UI）。
     */
    OrderDetailRespDTO queryOrderDetailNew(String orderToken);

    /**
     * 计算退款金额。
     */
    OrderCalculateRefundAmountRespDTO calculateRefundAmount(String orderToken);

    /**
     * 领取票务折扣。
     */
    Boolean ticketDiscountReceive(String ticketId);

    /**
     * 取消订单。
     */
    Boolean cancelOrder(OrderCancelOrderReqDTO form);

    /**
     * 删除订单。
     */
    Boolean delOrder(OrderDelOrderReqDTO form);

    /**
     * 申请退款。
     */
    Boolean applyRefund(OrderApplyRefundReqDTO form);

    /**
     * 退款确认。
     */
    Boolean orderRefundConfirm(OrderRefundConfirmReqDTO form);

    /**
     * 退票（余票处理）。
     */
    Boolean restTickets(Map<String, Object> form);
}
