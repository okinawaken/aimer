package cn.nicomoe.aimer.study.client.http.v1;

import cn.nicomoe.aimer.study.client.http.StudyRoomBaseClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatConfirmClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomEspLightTurnClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRankClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRankClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatConfirmClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlePackageClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlePackageClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlementClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlementClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignAnotherCheckClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignOutClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignOutClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatMapClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatQueryClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageConfirmClientRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.List;

/**
 * 签到域：签到、换座、签退、排行榜
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomSignClient extends StudyRoomBaseClient {

    /**
     * 签到首页：当前签到信息、附近门店、可选套餐
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @param venueId    门店 ID
     * @param bssid      BSSID
     * @param ssid       SSID
     * @return 响应数据
     */
    @Get(url = "/mp/study/index")
    StudyRoomClientResult<StudyRoomIndexClientRespDTO> studyIndex(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat, @Query(value = "venueId") Integer venueId, @Query(value = "bssid") String bssid, @Query(value = "ssid") String ssid);

    /**
     * 签到选座
     * <p>
     * 该接口有两种响应：普通用户返回座位图（{@code code=200}）；存在待签到预约的用户由上游直接完成签到，
     * 返回 {@code code=SV0001} + 「预约座位XXX签到成功！」文案（{@code data=1}）。
     * 两种形态由 {@link StudyRoomSignSeatListClientRespDTO} 统一承接。
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/study/seat/list")
    StudyRoomClientResult<StudyRoomSignSeatListClientRespDTO> studySeatList(@JSONBody StudyRoomSignSeatQueryClientReqDTO form);

    /**
     * 换座座位图
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/study/change/seat/list")
    StudyRoomClientResult<StudyRoomSignSeatMapClientRespDTO> studyChangeSeatList(@JSONBody StudyRoomChangeSeatClientReqDTO form);

    /**
     * 签退可选结算套餐（实测 {@code data} 是数组）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/study/settlePackage/select")
    StudyRoomClientResult<List<StudyRoomSettlePackageClientRespDTO>> studySettlePackageSelect(@JSONBody StudyRoomSettlePackageClientReqDTO form);

    /**
     * 是否可再签一个座位
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @Get(url = "/mp/study/signAnother/check")
    StudyRoomClientResult<StudyRoomSignAnotherCheckClientRespDTO> studySignAnotherCheck(@Query(value = "venueId") Integer venueId);

    /**
     * 签退（扫码签退），返回结算结果
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/study/signOut")
    StudyRoomClientResult<StudyRoomSignOutClientRespDTO> studySignOut(@JSONBody StudyRoomSignOutClientReqDTO form);

    /**
     * 学习排行榜
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/study/rank/index")
    StudyRoomClientResult<StudyRoomRankClientRespDTO> studyRank(@JSONBody StudyRoomRankClientReqDTO form);

    /**
     * 签到选座确认（真正入座）
     * <p>
     * 与 {@link #studySeatList} 配对使用：先取座位图选座，再调本接口落座。
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/study/seat/confirm")
    StudyRoomClientResult<StudyRoomSeatConfirmClientRespDTO> studySeatConfirm(@JSONBody StudyRoomSeatConfirmClientReqDTO form);

    /**
     * 换座确认
     * <p>
     * 与 {@link #studyChangeSeatList} 配对使用。未签到时调用会返回「还未签到，不能换座」。
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/study/change/seat/confirm")
    StudyRoomClientResult<StudyRoomChangeSeatConfirmClientRespDTO> studyChangeSeatConfirm(@JSONBody StudyRoomChangeSeatConfirmClientReqDTO form);

    /**
     * 进入签退结算：返回结算明细 + 可选结算套餐
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/study/settlement")
    StudyRoomClientResult<StudyRoomSettlementClientRespDTO> studySettlement(@JSONBody StudyRoomSettlementClientReqDTO form);

    /**
     * 确认签退结算套餐（选套餐扣费）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/study/userPackage/confirm")
    StudyRoomClientResult<StudyRoomUserPackageConfirmClientRespDTO> studyUserPackageConfirm(@JSONBody StudyRoomUserPackageConfirmClientReqDTO form);

    /**
     * 座位灯控（开灯 / 关灯）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/espLight/turn")
    StudyRoomClientResult<Void> espLightTurn(@JSONBody StudyRoomEspLightTurnClientReqDTO form);

}
