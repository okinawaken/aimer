package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.OrderApplyRefundReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderCalculateRefundAmountRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderCancelOrderReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderDelOrderReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderRefundConfirmReqDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.Map;

/**
 * 订单域。
 */
public interface AliyuOrderClient extends AliyuBaseClient {

    /**
     * 订单列表。
     */
    @Get(url = "/order/queryOrderList")
    AliyuClientResult<OrderListRespDTO> queryOrderList(@Query(value = "pageIndex") Integer pageIndex,
                                                       @Query(value = "pageSize") Integer pageSize,
                                                       @Query(value = "orderListType") String orderListType,
                                                       @Query(value = "needCount") Boolean needCount);

    /**
     * 订单详情。
     */
    @Get(url = "/order/queryOrderDetail")
    AliyuClientResult<OrderDetailRespDTO> queryOrderDetail(@Query(value = "orderToken") String orderToken,
                                                           @Query(value = "isNewUI") Boolean isNewUI);

    /**
     * 订单详情（新版 UI）。
     */
    @Get(url = "/order/queryOrderDetailNew")
    AliyuClientResult<OrderDetailRespDTO> queryOrderDetailNew(@Query(value = "orderToken") String orderToken);

    /**
     * 计算退款金额。
     */
    @Get(url = "/order/calculateRefundAmount")
    AliyuClientResult<OrderCalculateRefundAmountRespDTO> calculateRefundAmount(@Query(value = "orderToken") String orderToken);

    /**
     * 领取票务折扣。响应 {@code data} 为操作成功标识。
     */
    @Get(url = "/order/ticket/discount/receive")
    AliyuClientResult<Boolean> ticketDiscountReceive(@Query(value = "ticketId") String ticketId);

    /**
     * 取消订单。响应 {@code data} 为操作成功标识。
     */
    @Post(url = "/order/cancelOrder")
    AliyuClientResult<Boolean> cancelOrder(@JSONBody OrderCancelOrderReqDTO body);

    /**
     * 删除订单。响应 {@code data} 为操作成功标识。
     */
    @Post(url = "/order/delOrder")
    AliyuClientResult<Boolean> delOrder(@JSONBody OrderDelOrderReqDTO body);

    /**
     * 申请退款。响应 {@code data} 为操作成功标识。
     */
    @Post(url = "/order/applyRefund")
    AliyuClientResult<Boolean> applyRefund(@JSONBody OrderApplyRefundReqDTO body);

    /**
     * 退款确认。响应 {@code data} 为操作成功标识。
     */
    @Post(url = "/order/orderRefundConfirm")
    AliyuClientResult<Boolean> orderRefundConfirm(@JSONBody OrderRefundConfirmReqDTO body);

    /**
     * 退票（余票处理）。响应 {@code data} 为操作成功标识。
     */
    @Post(url = "/order/restTickets")
    AliyuClientResult<Boolean> restTickets(@JSONBody Map<String, Object> body);
}