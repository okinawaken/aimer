package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatConfirmClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomEspLightTurnClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientReqDTO;
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

import java.util.List;

/**
 * 自习室签到域服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomSignService {

    /**
     * 签到首页。
     *
     * @param form 请求参数
     * @return 签到首页数据
     */
    StudyRoomIndexClientRespDTO studyIndex(StudyRoomIndexClientReqDTO form);

    /**
     * 签到首页（指定用户）。
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 签到首页数据
     */
    StudyRoomIndexClientRespDTO studyIndex(StudyRoomIndexClientReqDTO form, Long userId);

    /**
     * 签到选座座位图。
     *
     * @param form 请求参数
     * @return 座位列表数据
     */
    StudyRoomSignSeatListClientRespDTO studySeatList(StudyRoomSignSeatQueryClientReqDTO form);

    /**
     * 签到选座座位图（指定用户）。
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 座位列表数据
     */
    StudyRoomSignSeatListClientRespDTO studySeatList(
            StudyRoomSignSeatQueryClientReqDTO form, Long userId);

    /**
     * 换座座位图。
     *
     * @param form 请求参数
     * @return 座位图数据
     */
    StudyRoomSignSeatMapClientRespDTO studyChangeSeatList(StudyRoomChangeSeatClientReqDTO form);

    /**
     * 签退可选结算套餐。
     *
     * @param form 请求参数
     * @return 结算套餐列表数据
     */
    List<StudyRoomSettlePackageClientRespDTO> studySettlePackageSelect(StudyRoomSettlePackageClientReqDTO form);

    /**
     * 签退可选结算套餐（指定用户）。
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 结算套餐列表数据
     */
    List<StudyRoomSettlePackageClientRespDTO> studySettlePackageSelect(StudyRoomSettlePackageClientReqDTO form, Long userId);

    /**
     * 是否可再签一个座位。
     *
     * @param venueId 门店 ID
     * @return 校验数据
     */
    StudyRoomSignAnotherCheckClientRespDTO studySignAnotherCheck(Integer venueId);

    /**
     * 签退。
     *
     * @param form 请求参数
     * @return 签退结果数据
     */
    StudyRoomSignOutClientRespDTO studySignOut(StudyRoomSignOutClientReqDTO form);

    /**
     * 签退（指定用户）。
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 签退结果数据
     */
    StudyRoomSignOutClientRespDTO studySignOut(StudyRoomSignOutClientReqDTO form, Long userId);

    /**
     * 学习排行榜。
     *
     * @param form 请求参数
     * @return 排行榜数据
     */
    StudyRoomRankClientRespDTO studyRank(StudyRoomRankClientReqDTO form);

    /**
     * 签到选座确认。
     *
     * @param form 请求参数
     * @return 入座结果数据
     */
    StudyRoomSeatConfirmClientRespDTO studySeatConfirm(StudyRoomSeatConfirmClientReqDTO form);

    /**
     * 换座确认。
     *
     * @param form 请求参数
     * @return 换座结果数据
     */
    StudyRoomChangeSeatConfirmClientRespDTO studyChangeSeatConfirm(StudyRoomChangeSeatConfirmClientReqDTO form);

    /**
     * 进入签退结算。
     *
     * @param form 请求参数
     * @return 结算数据
     */
    StudyRoomSettlementClientRespDTO studySettlement(StudyRoomSettlementClientReqDTO form);

    /**
     * 确认签退结算套餐。
     *
     * @param form 请求参数
     * @return 结算结果数据
     */
    StudyRoomUserPackageConfirmClientRespDTO studyUserPackageConfirm(StudyRoomUserPackageConfirmClientReqDTO form);

    /**
     * 座位灯控。
     *
     * @param form 请求参数
     */
    void espLightTurn(StudyRoomEspLightTurnClientReqDTO form);
}
