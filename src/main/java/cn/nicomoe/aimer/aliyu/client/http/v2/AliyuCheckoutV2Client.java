package cn.nicomoe.aimer.aliyu.client.http.v2;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.CheckoutDoPayReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.CheckoutDoPayRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.CheckoutRenderPayRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.InvoiceApplyInvoiceReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.InvoiceGetInvoicingOrderDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.InvoiceHistoryDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.InvoiceHistoryListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.InvoicingOrderListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MsgSendEmailMsgReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MsgSendMobileMsgReqDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Var;

/** 选座 / 支付 / 发票 / 消息域（显式用户上下文）。 */
public interface AliyuCheckoutV2Client extends AliyuBaseClient {

    /** 选座缓存预热。 */
    @Get(url = "/pms/chooseSeat/seatCachePreheat")
    AliyuClientResult<Boolean> seatCachePreheat(@Query(value = "projectId") String projectId,
                                                @Query(value = "eventId") String eventId,
                                                @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 渲染支付（卖座支付）。 */
    @Get(url = "/maipay2/renderPay")
    AliyuClientResult<CheckoutRenderPayRespDTO> renderPay(@Query(value = "supplierClass") String supplierClass,
                                                          @Query(value = "outerPaymentNo") String outerPaymentNo,
                                                          @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 发起支付（拉起微信支付）。 */
    @Post(url = "/maipay2/doPay")
    AliyuClientResult<CheckoutDoPayRespDTO> doPay(@JSONBody CheckoutDoPayReqDTO body,
                                                  @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 可开票订单列表。 参数 page：页码参数 pageSize：每页数量 */
    @Get(url = "/invoice/simple/getInvoicingOrderList")
    AliyuClientResult<InvoicingOrderListRespDTO> getInvoicingOrderList(@Query(value = "page") Integer page,
                                                                       @Query(value = "pageSize") Integer pageSize,
                                                                       @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 可开票订单详情。 */
    @Get(url = "/invoice/simple/getInvoicingOrderDetail")
    AliyuClientResult<InvoiceGetInvoicingOrderDetailRespDTO> getInvoicingOrderDetail(@Query(value = "orderId") String orderId,
                                                                                     @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 历史发票分页。 <p> 与 {@link #getInvoicingOrderList(Integer, Integer, Long)}（可开票订单）不同，这里返回已开票记录； {@code nextPage} 为 {@code true} 时以返回的 {@code page + 1} 继续请求。 参数 page：页码，从 1 开始（小程序传 1） 参数 pageSize：每页数量（小程序传 10） 参数 userId：系统用户 ID */
    @Get(url = "/invoice/simple/getInvoiceInfoList")
    AliyuClientResult<InvoiceHistoryListRespDTO> getInvoiceInfoList(@Query(value = "page") Integer page,
                                                                  @Query(value = "pageSize") Integer pageSize,
                                                                  @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 历史发票详情。 参数 tradeInvoiceId：发票记录 ID，取自历史列表行参数 userId：系统用户 ID */
    @Get(url = "/invoice/simple/getInvoiceDetails")
    AliyuClientResult<InvoiceHistoryDetailRespDTO> getInvoiceDetails(@Query(value = "tradeInvoiceId") String tradeInvoiceId,
                                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 开票灰度判断。 */
    @Get(url = "/invoice/simple/invoiceGray")
    AliyuClientResult<Boolean> invoiceGray(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 申请开票。 */
    @Post(url = "/invoice/simple/applyInvoice")
    AliyuClientResult<Object> applyInvoice(@JSONBody InvoiceApplyInvoiceReqDTO body,
                                           @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 发送手机短信验证码（入参含阿里安全滑块参数 {@code signParam, sig, sessionId, token}）。 */
    @Post(url = "/msg/sendMobileMsg")
    AliyuClientResult<Object> sendMobileMsg(@JSONBody MsgSendMobileMsgReqDTO body,
                                            @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 发送邮箱验证码（入参含阿里安全滑块参数 {@code signParam, sig, sessionId, token}）。 */
    @Post(url = "/msg/sendEmailMsg")
    AliyuClientResult<Object> sendEmailMsg(@JSONBody MsgSendEmailMsgReqDTO body,
                                           @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);
}
