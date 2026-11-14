package cn.nicomoe.aimer.study.service.client;

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

public interface StudyRoomPackageService {

    /** 套餐列表（旧版）。 */
    StudyRoomBuyPackageListClientRespDTO buyPackageList();

    /** 套餐列表（v2）。 */
    StudyRoomBuyV2PackageListClientRespDTO buyV2PackageList(StudyRoomBuyV2PackageListClientReqDTO form);

    /** 套餐筛选项。 */
    StudyRoomBuyV2PackageOptClientRespDTO buyV2PackageOptList(Double currentLng, Double currentLat);

    /** 套餐标签 / 座位区域筛选项。 */
    StudyRoomBuyV2PackageLabelClientRespDTO buyV2PackageLabelList(Integer venueId);

    /** 套餐详情（v2）。 */
    StudyRoomBuyV2PackageDetailClientRespDTO buyV2PackageDetail(Long packageId);

    /** 确认下单（旧版）。 */
    StudyRoomBuyOrderClientRespDTO buyPackageConfirm(StudyRoomBuyPackageConfirmClientReqDTO form);

    /** 确认下单（v2）。 */
    StudyRoomBuyOrderClientRespDTO buyV2PackageConfirm(StudyRoomBuyV2PackageConfirmClientReqDTO form);

    /** 微信支付下单，返回支付参数。 */
    StudyRoomWxPayParamClientRespDTO buyPackagePayOrder(StudyRoomBuyPayOrderClientReqDTO form);

    /** 我的订单。 */
    StudyRoomUserOrderListClientRespDTO userOrderList();

    /** 我的套餐。 */
    StudyRoomUserPackageListClientRespDTO userPackageList();

    /** 微信支付状态查询。 */
    StudyRoomWxPayQueryClientRespDTO wxPayQueryStatus(Long payId);
}
