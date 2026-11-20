package cn.nicomoe.aimer.aliyu.controller.client;

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
import cn.nicomoe.aimer.aliyu.model.vo.CheckoutDoPayVO;
import cn.nicomoe.aimer.aliyu.model.vo.CheckoutRenderPayVO;
import cn.nicomoe.aimer.aliyu.model.vo.InvoiceGetInvoicingOrderDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.InvoiceHistoryDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.InvoiceHistoryListVO;
import cn.nicomoe.aimer.aliyu.model.vo.InvoicingOrderListVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuCheckoutService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 阿里鱼选座 / 支付 / 发票 / 消息域接口。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/checkout")
public class AliyuCheckoutController {

    private final AliyuCheckoutService checkoutService;

    /**
     * 选座缓存预热。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/seat-cache-preheat")
    public R<Boolean> seatCachePreheat(@RequestParam(value = "projectId") String projectId,
                                       @RequestParam(value = "eventId") String eventId) {
        return R.ok(checkoutService.seatCachePreheat(projectId, eventId));
    }

    /**
     * 渲染支付（卖座支付）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/render-pay")
    public R<CheckoutRenderPayVO> renderPay(@RequestParam(value = "supplierClass") String supplierClass,
                                            @RequestParam(value = "outerPaymentNo") String outerPaymentNo) {
        CheckoutRenderPayRespDTO renderPayDTO = checkoutService.renderPay(supplierClass, outerPaymentNo);
        CheckoutRenderPayVO renderPayVO = MapstructUtils.convert(renderPayDTO, CheckoutRenderPayVO.class);
        return R.ok(renderPayVO);
    }

    /**
     * 发起支付（拉起微信支付）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/do-pay")
    public R<CheckoutDoPayVO> doPay(@RequestBody CheckoutDoPayReqDTO form) {
        CheckoutDoPayRespDTO doPayDTO = checkoutService.doPay(form);
        CheckoutDoPayVO doPayVO = MapstructUtils.convert(doPayDTO, CheckoutDoPayVO.class);
        return R.ok(doPayVO);
    }

    /**
     * 可开票订单列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/invoice/order/list")
    public R<InvoicingOrderListVO> getInvoicingOrderList(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        InvoicingOrderListRespDTO orderListDTO = checkoutService.getInvoicingOrderList(page, pageSize);
        InvoicingOrderListVO orderListVO = MapstructUtils.convert(orderListDTO, InvoicingOrderListVO.class);
        return R.ok(orderListVO);
    }

    /**
     * 可开票订单详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/invoice/order/detail")
    public R<InvoiceGetInvoicingOrderDetailVO> getInvoicingOrderDetail(
            @RequestParam(value = "orderId") String orderId) {
        InvoiceGetInvoicingOrderDetailRespDTO orderDetailDTO = checkoutService.getInvoicingOrderDetail(orderId);
        InvoiceGetInvoicingOrderDetailVO orderDetailVO = MapstructUtils.convert(orderDetailDTO, InvoiceGetInvoicingOrderDetailVO.class);
        return R.ok(orderDetailVO);
    }

    /**
     * 历史发票分页。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/invoice/history/list")
    public R<InvoiceHistoryListVO> getInvoiceInfoList(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        InvoiceHistoryListRespDTO invoiceListDTO = checkoutService.getInvoiceInfoList(page, pageSize);
        InvoiceHistoryListVO invoiceListVO = MapstructUtils.convert(invoiceListDTO, InvoiceHistoryListVO.class);
        return R.ok(invoiceListVO);
    }

    /**
     * 历史发票详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/invoice/history/detail")
    public R<InvoiceHistoryDetailVO> getInvoiceDetails(
            @RequestParam(value = "tradeInvoiceId") String tradeInvoiceId) {
        InvoiceHistoryDetailRespDTO invoiceDetailDTO = checkoutService.getInvoiceDetails(tradeInvoiceId);
        InvoiceHistoryDetailVO invoiceDetailVO = MapstructUtils.convert(invoiceDetailDTO, InvoiceHistoryDetailVO.class);
        return R.ok(invoiceDetailVO);
    }

    /**
     * 开票灰度判断。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/invoice/gray")
    public R<Boolean> invoiceGray() {
        return R.ok(checkoutService.invoiceGray());
    }

    /**
     * 申请开票。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/invoice/apply")
    public R<Boolean> applyInvoice(@RequestBody InvoiceApplyInvoiceReqDTO form) {
        return R.ok(checkoutService.applyInvoice(form));
    }

    /**
     * 发送手机短信验证码。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/msg/mobile")
    public R<Boolean> sendMobileMsg(@RequestBody MsgSendMobileMsgReqDTO form) {
        return R.ok(checkoutService.sendMobileMsg(form));
    }

    /**
     * 发送邮箱验证码。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/msg/email")
    public R<Boolean> sendEmailMsg(@RequestBody MsgSendEmailMsgReqDTO form) {
        return R.ok(checkoutService.sendEmailMsg(form));
    }
}
