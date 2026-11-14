package cn.nicomoe.aimer.aliyu.client.http.v2;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
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
import com.dtflys.forest.annotation.Var;

import java.util.Map;

/** 订单域（显式用户上下文）。列表、详情、取消、删除、退款、退票、票务折扣。 */
public interface AliyuOrderV2Client extends AliyuBaseClient {

    /** 订单列表。 */
    @Get(url = "/order/queryOrderList")
    AliyuClientResult<OrderListRespDTO> queryOrderList(@Query(value = "pageIndex") Integer pageIndex,
                                                       @Query(value = "pageSize") Integer pageSize,
                                                       @Query(value = "orderListType") String orderListType,
                                                       @Query(value = "needCount") Boolean needCount,
                                                       @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 订单详情。小程序固定传 {@code isNewUI=true}。 参数 orderToken：订单号参数 isNewUI：新版 UI 标识参数 userId：系统用户 ID */
    @Get(url = "/order/queryOrderDetail")
    AliyuClientResult<OrderDetailRespDTO> queryOrderDetail(@Query(value = "orderToken") String orderToken,
                                                           @Query(value = "isNewUI") Boolean isNewUI,
                                                           @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 订单详情（新版 UI）。 参数 orderToken：订单号 */
    @Get(url = "/order/queryOrderDetailNew")
    AliyuClientResult<OrderDetailRespDTO> queryOrderDetailNew(@Query(value = "orderToken") String orderToken,
                                                              @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 计算退款金额。 */
    @Get(url = "/order/calculateRefundAmount")
    AliyuClientResult<OrderCalculateRefundAmountRespDTO> calculateRefundAmount(@Query(value = "orderToken") String orderToken,
                                                                               @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 领取票务折扣。 参数 ticketId：票品 ID */
    @Get(url = "/order/ticket/discount/receive")
    AliyuClientResult<Object> ticketDiscountReceive(@Query(value = "ticketId") String ticketId,
                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 取消订单。 */
    @Post(url = "/order/cancelOrder")
    AliyuClientResult<Object> cancelOrder(@JSONBody OrderCancelOrderReqDTO body,
                                          @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 删除订单。 */
    @Post(url = "/order/delOrder")
    AliyuClientResult<Object> delOrder(@JSONBody OrderDelOrderReqDTO body,
                                       @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 申请退款。 */
    @Post(url = "/order/applyRefund")
    AliyuClientResult<Object> applyRefund(@JSONBody OrderApplyRefundReqDTO body,
                                          @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 退款确认。 */
    @Post(url = "/order/orderRefundConfirm")
    AliyuClientResult<Object> orderRefundConfirm(@JSONBody OrderRefundConfirmReqDTO body,
                                                 @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 退票（余票处理）。 */
    @Post(url = "/order/restTickets")
    AliyuClientResult<Object> restTickets(@JSONBody Map<String, Object> body,
                                          @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);
}