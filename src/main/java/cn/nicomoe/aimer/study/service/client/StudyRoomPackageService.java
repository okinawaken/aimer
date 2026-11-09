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

/**
 * 自习室套餐域服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomPackageService {

    /**
     * 套餐列表（旧版）。
     *
     * @return 套餐列表数据
     */
    StudyRoomBuyPackageListClientRespDTO buyPackageList();

    /**
     * 套餐列表（v2）。
     *
     * @param form 请求参数
     * @return 套餐列表数据
     */
    StudyRoomBuyV2PackageListClientRespDTO buyV2PackageList(StudyRoomBuyV2PackageListClientReqDTO form);

    /**
     * 套餐筛选项。
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 筛选项数据
     */
    StudyRoomBuyV2PackageOptClientRespDTO buyV2PackageOptList(Double currentLng, Double currentLat);

    /**
     * 套餐标签 / 座位区域筛选项。
     *
     * @param venueId 门店 ID
     * @return 标签数据
     */
    StudyRoomBuyV2PackageLabelClientRespDTO buyV2PackageLabelList(Integer venueId);

    /**
     * 套餐详情（v2）。
     *
     * @param packageId 套餐 ID
     * @return 套餐详情数据
     */
    StudyRoomBuyV2PackageDetailClientRespDTO buyV2PackageDetail(Long packageId);

    /**
     * 确认下单（旧版）。
     *
     * @param form 请求参数
     * @return 订单数据
     */
    StudyRoomBuyOrderClientRespDTO buyPackageConfirm(StudyRoomBuyPackageConfirmClientReqDTO form);

    /**
     * 确认下单（v2）。
     *
     * @param form 请求参数
     * @return 订单数据
     */
    StudyRoomBuyOrderClientRespDTO buyV2PackageConfirm(StudyRoomBuyV2PackageConfirmClientReqDTO form);

    /**
     * 微信支付下单。
     *
     * @param form 请求参数
     * @return 支付参数
     */
    StudyRoomWxPayParamClientRespDTO buyPackagePayOrder(StudyRoomBuyPayOrderClientReqDTO form);

    /**
     * 我的订单。
     *
     * @return 订单列表数据
     */
    StudyRoomUserOrderListClientRespDTO userOrderList();

    /**
     * 我的套餐。
     *
     * @return 套餐列表数据
     */
    StudyRoomUserPackageListClientRespDTO userPackageList();

    /**
     * 微信支付状态查询。
     *
     * @param payId 支付单 ID
     * @return 支付状态数据
     */
    StudyRoomWxPayQueryClientRespDTO wxPayQueryStatus(Long payId);
}
