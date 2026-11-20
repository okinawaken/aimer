package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.OrderApplyRefundReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderCalculateRefundAmountRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderCancelOrderReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderDelOrderReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.OrderRefundConfirmReqDTO;
import cn.nicomoe.aimer.aliyu.model.vo.OrderCalculateRefundAmountVO;
import cn.nicomoe.aimer.aliyu.model.vo.OrderDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.OrderListVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuOrderService;
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

import java.util.Map;

/**
 * 阿里鱼订单域接口：列表、详情、退款与售后。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/order")
public class AliyuOrderController {

    private final AliyuOrderService orderService;

    /**
     * 订单列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/list")
    public R<OrderListVO> queryOrderList(
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "orderListType", required = false) String orderListType,
            @RequestParam(value = "needCount", required = false) Boolean needCount) {
        OrderListRespDTO orderListDTO = orderService.queryOrderList(pageIndex, pageSize, orderListType, needCount);
        OrderListVO orderListVO = MapstructUtils.convert(orderListDTO, OrderListVO.class);
        return R.ok(orderListVO);
    }

    /**
     * 订单详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/detail")
    public R<OrderDetailVO> queryOrderDetail(
            @RequestParam(value = "orderToken") String orderToken,
            @RequestParam(value = "isNewUI", required = false) Boolean isNewUI) {
        OrderDetailRespDTO orderDetailDTO = orderService.queryOrderDetail(orderToken, isNewUI);
        OrderDetailVO orderDetailVO = MapstructUtils.convert(orderDetailDTO, OrderDetailVO.class);
        return R.ok(orderDetailVO);
    }

    /**
     * 订单详情（新版 UI）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/detail/new")
    public R<OrderDetailVO> queryOrderDetailNew(
            @RequestParam(value = "orderToken") String orderToken) {
        OrderDetailRespDTO orderDetailDTO = orderService.queryOrderDetailNew(orderToken);
        OrderDetailVO orderDetailVO = MapstructUtils.convert(orderDetailDTO, OrderDetailVO.class);
        return R.ok(orderDetailVO);
    }

    /**
     * 计算退款金额。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/refund-amount")
    public R<OrderCalculateRefundAmountVO> calculateRefundAmount(
            @RequestParam(value = "orderToken") String orderToken) {
        OrderCalculateRefundAmountRespDTO refundAmountDTO = orderService.calculateRefundAmount(orderToken);
        OrderCalculateRefundAmountVO refundAmountVO = MapstructUtils.convert(refundAmountDTO, OrderCalculateRefundAmountVO.class);
        return R.ok(refundAmountVO);
    }

    /**
     * 领取票务折扣。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/ticket-discount/receive")
    public R<Boolean> ticketDiscountReceive(@RequestParam(value = "ticketId") String ticketId) {
        return R.ok(orderService.ticketDiscountReceive(ticketId));
    }

    /**
     * 取消订单。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cancel")
    public R<Boolean> cancelOrder(@RequestBody OrderCancelOrderReqDTO form) {
        return R.ok(orderService.cancelOrder(form));
    }

    /**
     * 删除订单。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/delete")
    public R<Boolean> delOrder(@RequestBody OrderDelOrderReqDTO form) {
        return R.ok(orderService.delOrder(form));
    }

    /**
     * 申请退款。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/refund/apply")
    public R<Boolean> applyRefund(@RequestBody OrderApplyRefundReqDTO form) {
        return R.ok(orderService.applyRefund(form));
    }

    /**
     * 退款确认。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/refund/confirm")
    public R<Boolean> orderRefundConfirm(@RequestBody OrderRefundConfirmReqDTO form) {
        return R.ok(orderService.orderRefundConfirm(form));
    }

    /**
     * 退票（余票处理）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/rest-tickets")
    public R<Boolean> restTickets(@RequestBody Map<String, Object> form) {
        return R.ok(orderService.restTickets(form));
    }
}
