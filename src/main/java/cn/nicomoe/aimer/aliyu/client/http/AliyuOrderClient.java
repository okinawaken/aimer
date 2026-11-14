package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.Map;
import cn.nicomoe.aimer.aliyu.model.dto.resp.OrderListRespDTO;

/**
 * 订单域：{@code /order}。列表、详情、取消、删除、退款、退票、票务折扣。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuOrderClient extends AliyuBaseClient {

    /**
     * 订单列表（入参 {@code pageIndex, pageSize, orderListType, needCount}）。
     *
     * @param pageIndex     页码
     * @param pageSize      每页数量
     * @param orderListType 订单列表类型
     * @param needCount     是否需要总数
     * @return 响应数据
     */
    @Get(url = "/order/queryOrderList")
    AliyuClientResult<OrderListRespDTO> queryOrderList(@Query(value = "pageIndex") Integer pageIndex,
                                             @Query(value = "pageSize") Integer pageSize,
                                             @Query(value = "orderListType") String orderListType,
                                             @Query(value = "needCount") Boolean needCount);

    /**
     * 订单详情。
     *
     * @return 响应数据
     */
    @Get(url = "/order/queryOrderDetail")
    AliyuClientResult<Object> queryOrderDetail();

    /**
     * 订单详情（新版 UI）。
     *
     * @return 响应数据
     */
    @Get(url = "/order/queryOrderDetailNew")
    AliyuClientResult<Object> queryOrderDetailNew();

    /**
     * 计算退款金额。
     *
     * @param orderToken 订单 token
     * @return 响应数据
     */
    @Get(url = "/order/calculateRefundAmount")
    AliyuClientResult<Object> calculateRefundAmount(@Query(value = "orderToken") String orderToken);

    /**
     * 领取票务折扣。
     *
     * @return 响应数据
     */
    @Get(url = "/order/ticket/discount/receive")
    AliyuClientResult<Object> ticketDiscountReceive();

    /**
     * 取消订单。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/order/cancelOrder")
    AliyuClientResult<Object> cancelOrder(@JSONBody Map<String, Object> body);

    /**
     * 删除订单。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/order/delOrder")
    AliyuClientResult<Object> delOrder(@JSONBody Map<String, Object> body);

    /**
     * 申请退款。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/order/applyRefund")
    AliyuClientResult<Object> applyRefund(@JSONBody Map<String, Object> body);

    /**
     * 退款确认（入参 {@code orderToken}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/order/orderRefundConfirm")
    AliyuClientResult<Object> orderRefundConfirm(@JSONBody Map<String, Object> body);

    /**
     * 退票（余票处理）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/order/restTickets")
    AliyuClientResult<Object> restTickets(@JSONBody Map<String, Object> body);
}
