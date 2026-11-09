package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveAnotherCheckClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveCheckClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveConfirmClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveConfirmV1ClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveDateClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatMapClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatQueryClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatQueryV1ClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveCancelClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveCancelTipClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveListClientRespDTO;

/**
 * 自习室预约域服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomReserveService {

    /**
     * 预约资格校验。
     *
     * @return 校验数据
     */
    StudyRoomReserveCheckClientRespDTO reserveCheck();

    /**
     * 预约资格校验（指定用户）。
     *
     * @param userId 用户 ID
     * @return 校验数据
     */
    StudyRoomReserveCheckClientRespDTO reserveCheck(Long userId);

    /**
     * 可预约日期列表。
     *
     * @param venueId 门店 ID
     * @return 日期列表数据
     */
    StudyRoomReserveDateClientRespDTO reserveDateList(Integer venueId);

    /**
     * 可预约日期列表（指定用户）。
     *
     * @param venueId 门店 ID
     * @param userId  用户 ID
     * @return 日期列表数据
     */
    StudyRoomReserveDateClientRespDTO reserveDateList(Integer venueId, Long userId);

    /**
     * 座位图（v2）。
     *
     * @param form 请求参数
     * @return 座位图数据
     */
    StudyRoomSeatMapClientRespDTO reserveV2SeatList(StudyRoomSeatQueryClientReqDTO form);

    /**
     * 座位图（v2，指定用户）。
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 座位图数据
     */
    StudyRoomSeatMapClientRespDTO reserveV2SeatList(StudyRoomSeatQueryClientReqDTO form, Long userId);

    /**
     * 提交预约（v2）。
     *
     * @param form 请求参数
     * @return 预约结果数据
     */
    StudyRoomReserveConfirmClientRespDTO reserveV2SeatConfirm(StudyRoomReserveConfirmClientReqDTO form);

    /**
     * 提交预约（v2，指定用户）。
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 预约结果数据
     */
    StudyRoomReserveConfirmClientRespDTO reserveV2SeatConfirm(
            StudyRoomReserveConfirmClientReqDTO form, Long userId);

    /**
     * 座位图（v1）。
     *
     * @param form 请求参数
     * @return 座位图数据
     */
    StudyRoomSeatMapClientRespDTO reserveSeatList(StudyRoomSeatQueryV1ClientReqDTO form);

    /**
     * 提交预约（v1）。
     *
     * @param form 请求参数
     * @return 预约结果数据
     */
    StudyRoomReserveConfirmClientRespDTO reserveSeatConfirm(StudyRoomReserveConfirmV1ClientReqDTO form);

    /**
     * 是否还能再预约一个座位。
     *
     * @param userReserveId 用户预约 ID
     * @return 校验数据
     */
    StudyRoomReserveAnotherCheckClientRespDTO reserveAnotherCheck(Long userReserveId);

    /**
     * 我的预约列表。
     *
     * @return 预约列表数据
     */
    StudyRoomUserReserveListClientRespDTO userReserveList();

    /**
     * 我的预约列表（指定用户）。
     *
     * @param userId 用户 ID
     * @return 预约列表数据
     */
    StudyRoomUserReserveListClientRespDTO userReserveList(Long userId);

    /**
     * 取消预约前的提示。
     *
     * @param userReserveId 用户预约 ID
     * @return 提示数据
     */
    StudyRoomUserReserveCancelTipClientRespDTO userReserveCancelTip(Long userReserveId);

    /**
     * 取消预约。
     *
     * @param form 请求参数
     * @return 处理结果
     */
    Integer userReserveCancel(StudyRoomUserReserveCancelClientReqDTO form);
}
