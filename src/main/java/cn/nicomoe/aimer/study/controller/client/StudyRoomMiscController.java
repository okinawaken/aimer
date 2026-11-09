package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetGridListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetGridPriceClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseCommitClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseCommitClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseOrderListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseOverduePayClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetUserGridListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetVenueListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetWifiOpenClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomConsumeResultClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVerifyClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReceiptConsumeClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomWxPayParamClientRespDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomCabinetGridListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomCabinetGridPriceVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomCabinetLeaseCommitVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomCabinetLeaseOrderListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomCabinetLeaseOverduePayVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomCabinetUserGridListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomCabinetVenueListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomConsumeResultVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomWxPayParamVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomMiscService;
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
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.core.view.R;

/**
 * 自习室其他域接口：储物柜、团购券核销
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/misc")
public class StudyRoomMiscController {

    private final StudyRoomMiscService miscService;

    /**
     * 储物柜租赁订单（无业务参数）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cabinet/lease/order/list")
    public R<StudyRoomCabinetLeaseOrderListVO> cabinetLeaseOrderList() {
        StudyRoomCabinetLeaseOrderListClientRespDTO leaseOrderListDTO = miscService.cabinetLeaseOrderList();
        StudyRoomCabinetLeaseOrderListVO leaseOrderListVO = MapstructUtils.convert(leaseOrderListDTO, StudyRoomCabinetLeaseOrderListVO.class);
        return R.ok(leaseOrderListVO);
    }

    /**
     * 储物柜租赁支付（参数走 query，leaseId 必须是整数）
     *
     * @param leaseId 租约 ID
     * @param userId  用户 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cabinet/lease/pay")
    public R<StudyRoomWxPayParamVO> cabinetLeasePay(@RequestParam(value = "leaseId") Long leaseId, @RequestParam(value = "userId") Long userId) {
        StudyRoomWxPayParamClientRespDTO wxPayParamDTO = miscService.cabinetLeasePay(leaseId, userId);
        StudyRoomWxPayParamVO wxPayParamVO = MapstructUtils.convert(wxPayParamDTO, StudyRoomWxPayParamVO.class);
        return R.ok(wxPayParamVO);
    }

    /**
     * 储物柜下单，返回租约 ID
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cabinet/lease/commit")
    public R<StudyRoomCabinetLeaseCommitVO> cabinetLeaseCommit(@RequestBody StudyRoomCabinetLeaseCommitClientReqDTO form) {
        StudyRoomCabinetLeaseCommitClientRespDTO leaseCommitDTO = miscService.cabinetLeaseCommit(form);
        StudyRoomCabinetLeaseCommitVO leaseCommitVO = MapstructUtils.convert(leaseCommitDTO, StudyRoomCabinetLeaseCommitVO.class);
        return R.ok(leaseCommitVO);
    }

    /**
     * 储物柜逾期支付（参数走 query）
     *
     * @param leaseId  租约 ID
     * @param userCode 用户编码
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cabinet/lease/overdue/pay")
    public R<StudyRoomCabinetLeaseOverduePayVO> cabinetLeaseOverduePay(@RequestParam(value = "leaseId") Long leaseId, @RequestParam(value = "userCode") String userCode) {
        StudyRoomCabinetLeaseOverduePayClientRespDTO overduePayDTO = miscService.cabinetLeaseOverduePay(leaseId, userCode);
        StudyRoomCabinetLeaseOverduePayVO overduePayVO = MapstructUtils.convert(overduePayDTO, StudyRoomCabinetLeaseOverduePayVO.class);
        return R.ok(overduePayVO);
    }

    /**
     * 储物柜自动退租（按最近一次租约）
     *
     * @param lastLeaseId 最近一次租约 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/cabinet/lease/auto-checkout")
    public R<Void> cabinetLeaseAutoCheckout(@RequestParam(value = "lastLeaseId") Long lastLeaseId) {
        miscService.cabinetLeaseAutoCheckout(lastLeaseId);
        return R.ok();
    }

    /**
     * 储物柜开锁（WiFi 开锁，按柜格）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cabinet/wifi/open")
    public R<Void> cabinetWifiOpen(@RequestBody StudyRoomCabinetWifiOpenClientReqDTO form) {
        miscService.cabinetWifiOpen(form);
        return R.ok();
    }

    /**
     * 门店的储物柜列表
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/cabinet/venue/list")
    public R<StudyRoomCabinetVenueListVO> cabinetVenueList(@RequestParam(value = "venueId") Integer venueId) {
        StudyRoomCabinetVenueListClientRespDTO cabinetVenueListDTO = miscService.cabinetVenueList(venueId);
        StudyRoomCabinetVenueListVO cabinetVenueListVO = MapstructUtils.convert(cabinetVenueListDTO, StudyRoomCabinetVenueListVO.class);
        return R.ok(cabinetVenueListVO);
    }

    /**
     * 柜体的格口列表
     *
     * @param cabinetId 柜体 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/cabinet/grid/list")
    public R<StudyRoomCabinetGridListVO> cabinetGridList(@RequestParam(value = "cabinetId") Integer cabinetId) {
        StudyRoomCabinetGridListClientRespDTO cabinetGridListDTO = miscService.cabinetGridList(cabinetId);
        StudyRoomCabinetGridListVO cabinetGridListVO = MapstructUtils.convert(cabinetGridListDTO, StudyRoomCabinetGridListVO.class);
        return R.ok(cabinetGridListVO);
    }

    /**
     * 我当前已租的柜格列表
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/cabinet/user/grid/list")
    public R<StudyRoomCabinetUserGridListVO> cabinetUserGridList(@RequestParam(value = "venueId") Integer venueId) {
        StudyRoomCabinetUserGridListClientRespDTO userGridListDTO = miscService.cabinetUserGridList(venueId);
        StudyRoomCabinetUserGridListVO userGridListVO = MapstructUtils.convert(userGridListDTO, StudyRoomCabinetUserGridListVO.class);
        return R.ok(userGridListVO);
    }

    /**
     * 柜格定价
     *
     * @param gridId 格口 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/cabinet/grid/price")
    public R<StudyRoomCabinetGridPriceVO> cabinetGridPrice(@RequestParam(value = "gridId") Integer gridId) {
        StudyRoomCabinetGridPriceClientRespDTO gridPriceDTO = miscService.cabinetGridPrice(gridId);
        StudyRoomCabinetGridPriceVO gridPriceVO = MapstructUtils.convert(gridPriceDTO, StudyRoomCabinetGridPriceVO.class);
        return R.ok(gridPriceVO);
    }

    /**
     * 美团团购券核销
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/receipt/consume")
    public R<StudyRoomConsumeResultVO> receiptConsume(@RequestBody StudyRoomReceiptConsumeClientReqDTO form) {
        StudyRoomConsumeResultClientRespDTO consumeResultDTO = miscService.receiptConsume(form);
        StudyRoomConsumeResultVO consumeResultVO = MapstructUtils.convert(consumeResultDTO, StudyRoomConsumeResultVO.class);
        return R.ok(consumeResultVO);
    }

    /**
     * 抖音券核销
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/douyin/verify")
    public R<StudyRoomConsumeResultVO> douYinVerify(@RequestBody StudyRoomDouYinVerifyClientReqDTO form) {
        StudyRoomConsumeResultClientRespDTO consumeResultDTO = miscService.douYinVerify(form);
        StudyRoomConsumeResultVO consumeResultVO = MapstructUtils.convert(consumeResultDTO, StudyRoomConsumeResultVO.class);
        return R.ok(consumeResultVO);
    }

    /**
     * 图片上传（当前不支持）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/image/upload")
    public R<Void> uploadImage() {
        throw new EruptWebApiRuntimeException("暂不支持文件上传");
    }
}
