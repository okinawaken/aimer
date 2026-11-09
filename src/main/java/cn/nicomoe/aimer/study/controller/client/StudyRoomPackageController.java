package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyOrderClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyPackageConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyPackageListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyPayOrderClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageDetailClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageLabelClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageListClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageOptClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserOrderListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomWxPayParamClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomWxPayQueryClientRespDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomBuyOrderVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomBuyPackageListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomBuyV2PackageDetailVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomBuyV2PackageLabelVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomBuyV2PackageListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomBuyV2PackageOptVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserOrderListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserPackageListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomWxPayParamVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomWxPayQueryVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomPackageService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 自习室套餐域接口：套餐浏览、下单、支付、我的订单与套餐
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/package")
public class StudyRoomPackageController {

    private final StudyRoomPackageService packageService;

    /**
     * 套餐列表（旧版，按区域分组）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/list")
    public R<StudyRoomBuyPackageListVO> buyPackageList() {
        StudyRoomBuyPackageListClientRespDTO packageListDTO = packageService.buyPackageList();
        StudyRoomBuyPackageListVO packageListVO = MapstructUtils.convert(packageListDTO, StudyRoomBuyPackageListVO.class);
        return R.ok(packageListVO);
    }

    /**
     * 套餐列表（v2，按门店/区域/类型筛选）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/v2/list")
    public R<StudyRoomBuyV2PackageListVO> buyV2PackageList(@RequestBody StudyRoomBuyV2PackageListClientReqDTO form) {
        StudyRoomBuyV2PackageListClientRespDTO packageListDTO = packageService.buyV2PackageList(form);
        StudyRoomBuyV2PackageListVO packageListVO = MapstructUtils.convert(packageListDTO, StudyRoomBuyV2PackageListVO.class);
        return R.ok(packageListVO);
    }

    /**
     * 套餐筛选项（门店、类型、标签）
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/v2/opt-list")
    public R<StudyRoomBuyV2PackageOptVO> buyV2PackageOptList(@RequestParam(name = "currentLng", required = false) Double currentLng, @RequestParam(name = "currentLat", required = false) Double currentLat) {
        StudyRoomBuyV2PackageOptClientRespDTO packageOptDTO = packageService.buyV2PackageOptList(currentLng, currentLat);
        StudyRoomBuyV2PackageOptVO packageOptVO = MapstructUtils.convert(packageOptDTO, StudyRoomBuyV2PackageOptVO.class);
        return R.ok(packageOptVO);
    }

    /**
     * 套餐标签 / 座位区域筛选项
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/v2/label/select-list")
    public R<StudyRoomBuyV2PackageLabelVO> buyV2PackageLabelList(@RequestParam(value = "venueId") Integer venueId) {
        StudyRoomBuyV2PackageLabelClientRespDTO packageLabelDTO = packageService.buyV2PackageLabelList(venueId);
        StudyRoomBuyV2PackageLabelVO packageLabelVO = MapstructUtils.convert(packageLabelDTO, StudyRoomBuyV2PackageLabelVO.class);
        return R.ok(packageLabelVO);
    }

    /**
     * 套餐详情（v2）
     *
     * @param packageId 套餐 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/v2/detail/{packageId}")
    public R<StudyRoomBuyV2PackageDetailVO> buyV2PackageDetail(@PathVariable(value = "packageId") Long packageId) {
        StudyRoomBuyV2PackageDetailClientRespDTO packageDetailDTO = packageService.buyV2PackageDetail(packageId);
        StudyRoomBuyV2PackageDetailVO packageDetailVO = MapstructUtils.convert(packageDetailDTO, StudyRoomBuyV2PackageDetailVO.class);
        return R.ok(packageDetailVO);
    }

    /**
     * 确认下单（旧版）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/confirm")
    public R<StudyRoomBuyOrderVO> buyPackageConfirm(@RequestBody StudyRoomBuyPackageConfirmClientReqDTO form) {
        StudyRoomBuyOrderClientRespDTO buyOrderDTO = packageService.buyPackageConfirm(form);
        StudyRoomBuyOrderVO buyOrderVO = MapstructUtils.convert(buyOrderDTO, StudyRoomBuyOrderVO.class);
        return R.ok(buyOrderVO);
    }

    /**
     * 确认下单（v2）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/v2/confirm")
    public R<StudyRoomBuyOrderVO> buyV2PackageConfirm(@RequestBody StudyRoomBuyV2PackageConfirmClientReqDTO form) {
        StudyRoomBuyOrderClientRespDTO buyOrderDTO = packageService.buyV2PackageConfirm(form);
        StudyRoomBuyOrderVO buyOrderVO = MapstructUtils.convert(buyOrderDTO, StudyRoomBuyOrderVO.class);
        return R.ok(buyOrderVO);
    }

    /**
     * 微信支付下单（返回小程序 wx.requestPayment 所需参数）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/pay-order")
    public R<StudyRoomWxPayParamVO> buyPackagePayOrder(@RequestBody StudyRoomBuyPayOrderClientReqDTO form) {
        StudyRoomWxPayParamClientRespDTO wxPayParamDTO = packageService.buyPackagePayOrder(form);
        StudyRoomWxPayParamVO wxPayParamVO = MapstructUtils.convert(wxPayParamDTO, StudyRoomWxPayParamVO.class);
        return R.ok(wxPayParamVO);
    }

    /**
     * 我的订单
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/user/order/list")
    public R<StudyRoomUserOrderListVO> userOrderList() {
        StudyRoomUserOrderListClientRespDTO orderListDTO = packageService.userOrderList();
        StudyRoomUserOrderListVO orderListVO = MapstructUtils.convert(orderListDTO, StudyRoomUserOrderListVO.class);
        return R.ok(orderListVO);
    }

    /**
     * 我的套餐
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/user/package/list")
    public R<StudyRoomUserPackageListVO> userPackageList() {
        StudyRoomUserPackageListClientRespDTO packageListDTO = packageService.userPackageList();
        StudyRoomUserPackageListVO packageListVO = MapstructUtils.convert(packageListDTO, StudyRoomUserPackageListVO.class);
        return R.ok(packageListVO);
    }

    /**
     * 微信支付状态查询（按支付单 ID）
     *
     * @param payId 支付单 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/wx/query/status/{payId}")
    public R<StudyRoomWxPayQueryVO> wxPayQueryStatus(@PathVariable(value = "payId") Long payId) {
        StudyRoomWxPayQueryClientRespDTO wxPayQueryDTO = packageService.wxPayQueryStatus(payId);
        StudyRoomWxPayQueryVO wxPayQueryVO = MapstructUtils.convert(wxPayQueryDTO, StudyRoomWxPayQueryVO.class);
        return R.ok(wxPayQueryVO);
    }
}
