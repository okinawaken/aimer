package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuCheckoutClient;
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
import cn.nicomoe.aimer.aliyu.service.client.AliyuCheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/** 阿里鱼选座 / 支付 / 发票 / 消息域服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuCheckoutServiceImpl implements AliyuCheckoutService {

    private final AliyuCheckoutClient checkoutClient;

    @Override
    public Boolean seatCachePreheat(String projectId, String eventId) {
        AliyuClientResult<Boolean> result = checkoutClient.seatCachePreheat(projectId, eventId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public CheckoutRenderPayRespDTO renderPay(String supplierClass, String outerPaymentNo) {
        AliyuClientResult<CheckoutRenderPayRespDTO> result = checkoutClient.renderPay(supplierClass, outerPaymentNo);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public CheckoutDoPayRespDTO doPay(CheckoutDoPayReqDTO form) {
        AliyuClientResult<CheckoutDoPayRespDTO> result = checkoutClient.doPay(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public InvoicingOrderListRespDTO getInvoicingOrderList(Integer page, Integer pageSize) {
        AliyuClientResult<InvoicingOrderListRespDTO> result = checkoutClient.getInvoicingOrderList(page, pageSize);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public InvoiceGetInvoicingOrderDetailRespDTO getInvoicingOrderDetail(String orderId) {
        AliyuClientResult<InvoiceGetInvoicingOrderDetailRespDTO> result = checkoutClient.getInvoicingOrderDetail(orderId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public InvoiceHistoryListRespDTO getInvoiceInfoList(Integer page, Integer pageSize) {
        AliyuClientResult<InvoiceHistoryListRespDTO> result = checkoutClient.getInvoiceInfoList(page, pageSize);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public InvoiceHistoryDetailRespDTO getInvoiceDetails(String tradeInvoiceId) {
        AliyuClientResult<InvoiceHistoryDetailRespDTO> result = checkoutClient.getInvoiceDetails(tradeInvoiceId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean invoiceGray() {
        AliyuClientResult<Boolean> result = checkoutClient.invoiceGray();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object applyInvoice(InvoiceApplyInvoiceReqDTO form) {
        AliyuClientResult<Object> result = checkoutClient.applyInvoice(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object sendMobileMsg(MsgSendMobileMsgReqDTO form) {
        AliyuClientResult<Object> result = checkoutClient.sendMobileMsg(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object sendEmailMsg(MsgSendEmailMsgReqDTO form) {
        AliyuClientResult<Object> result = checkoutClient.sendEmailMsg(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
