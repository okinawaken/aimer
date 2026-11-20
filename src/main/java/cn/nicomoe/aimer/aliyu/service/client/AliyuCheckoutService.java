package cn.nicomoe.aimer.aliyu.service.client;

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

/** 阿里鱼选座 / 支付 / 发票 / 消息域服务，返回已解包的业务数据。 */
public interface AliyuCheckoutService {

    /** 选座缓存预热。 */
    Boolean seatCachePreheat(String projectId, String eventId);

    /** 渲染支付（卖座支付）。 */
    CheckoutRenderPayRespDTO renderPay(String supplierClass, String outerPaymentNo);

    /** 发起支付（拉起微信支付）。 */
    CheckoutDoPayRespDTO doPay(CheckoutDoPayReqDTO form);

    /** 可开票订单列表。 */
    InvoicingOrderListRespDTO getInvoicingOrderList(Integer page, Integer pageSize);

    /** 可开票订单详情。 */
    InvoiceGetInvoicingOrderDetailRespDTO getInvoicingOrderDetail(String orderId);

    /** 历史发票分页。 */
    InvoiceHistoryListRespDTO getInvoiceInfoList(Integer page, Integer pageSize);

    /** 历史发票详情。 */
    InvoiceHistoryDetailRespDTO getInvoiceDetails(String tradeInvoiceId);

    /** 开票灰度判断。 */
    Boolean invoiceGray();

    /** 申请开票。 */
    Object applyInvoice(InvoiceApplyInvoiceReqDTO form);

    /** 发送手机短信验证码。 */
    Object sendMobileMsg(MsgSendMobileMsgReqDTO form);

    /** 发送邮箱验证码。 */
    Object sendEmailMsg(MsgSendEmailMsgReqDTO form);
}
