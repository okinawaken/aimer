package cn.nicomoe.aimer.study.client.http.v1;

import cn.nicomoe.aimer.study.client.http.StudyRoomBaseClient;
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
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserOrderListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomWxPayParamClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomWxPayQueryClientRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Var;

/**
 * 套餐域：套餐浏览、下单、支付、我的订单与套餐
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomPackageClient extends StudyRoomBaseClient {

    /**
     * 套餐列表（旧版，按区域分组）
     *
     * @return 响应数据
     */
    @Get(url = "/mp/buy/package/list", timeout = 15000, readTimeout = 15000)
    StudyRoomClientResult<StudyRoomBuyPackageListClientRespDTO> buyPackageList();

    /**
     * 套餐列表（v2，按门店/区域/类型筛选）
     * <p>
     * 入参实测为 {@code {packageType, packageLabelIds, venueId, seatAreaId}}
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/buy/v2/package/list")
    StudyRoomClientResult<StudyRoomBuyV2PackageListClientRespDTO> buyV2PackageList(@JSONBody StudyRoomBuyV2PackageListClientReqDTO form);

    /**
     * 套餐筛选项（门店、类型、标签）
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 响应数据
     */
    @Get(url = "/mp/buy/v2/package/optList")
    StudyRoomClientResult<StudyRoomBuyV2PackageOptClientRespDTO> buyV2PackageOptList(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat);

    /**
     * 套餐标签 / 座位区域筛选项
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @Get(url = "/mp/buy/v2/package/label/selectList")
    StudyRoomClientResult<StudyRoomBuyV2PackageLabelClientRespDTO> buyV2PackageLabelList(@Query(value = "venueId") Integer venueId);

    /**
     * 套餐详情（v2）
     *
     * @param packageId 套餐 ID
     * @return 响应数据
     */
    @Get(url = "/mp/buy/v2/package/detail/${packageId}")
    StudyRoomClientResult<StudyRoomBuyV2PackageDetailClientRespDTO> buyV2PackageDetail(@Var(value = "packageId") Long packageId);

    /**
     * 确认下单（旧版）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/buy/package/confirm")
    StudyRoomClientResult<StudyRoomBuyOrderClientRespDTO> buyPackageConfirm(@JSONBody StudyRoomBuyPackageConfirmClientReqDTO form);

    /**
     * 确认下单（v2）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/buy/v2/package/confirm")
    StudyRoomClientResult<StudyRoomBuyOrderClientRespDTO> buyV2PackageConfirm(@JSONBody StudyRoomBuyV2PackageConfirmClientReqDTO form);

    /**
     * 微信支付下单（返回小程序 wx.requestPayment 所需参数）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/buy/package/payOrder")
    StudyRoomClientResult<StudyRoomWxPayParamClientRespDTO> buyPackagePayOrder(@JSONBody StudyRoomBuyPayOrderClientReqDTO form);

    /**
     * 我的订单
     *
     * @return 响应数据
     */
    @Get(url = "/mp/user/userOrder/list")
    StudyRoomClientResult<StudyRoomUserOrderListClientRespDTO> userOrderList();

    /**
     * 我的套餐
     *
     * @return 响应数据
     */
    @Get(url = "/mp/user/userPackage/list")
    StudyRoomClientResult<StudyRoomUserPackageListClientRespDTO> userPackageList();

    /**
     * 微信支付状态查询（按支付单ID）
     * <p>
     * 支付完成后轮询该接口确认是否到账；{@code status=-10} 表示查不到该支付单。
     *
     * @param payId 支付单 ID
     * @return 响应数据
     */
    @Post(url = "/mp/wx/payment/v2/wx/query/status/{payId}")
    StudyRoomClientResult<StudyRoomWxPayQueryClientRespDTO> wxPayQueryStatus(@Var(value = "payId") Long payId);

}
