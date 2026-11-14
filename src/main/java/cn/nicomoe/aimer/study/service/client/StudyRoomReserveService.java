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

import java.util.List;

public interface StudyRoomReserveService {

    /** 预约资格校验。 */
    StudyRoomReserveCheckClientRespDTO reserveCheck();

    /** 预约资格校验（指定用户）。 */
    StudyRoomReserveCheckClientRespDTO reserveCheck(Long userId);

    /** 可预约日期列表。 */
    StudyRoomReserveDateClientRespDTO reserveDateList(Integer venueId);

    /** 可预约日期列表（指定用户）。 */
    StudyRoomReserveDateClientRespDTO reserveDateList(Integer venueId, Long userId);

    /** 座位图（v2）。 */
    StudyRoomSeatMapClientRespDTO reserveV2SeatList(StudyRoomSeatQueryClientReqDTO form);

    /** 座位图（v2，指定用户）。 */
    StudyRoomSeatMapClientRespDTO reserveV2SeatList(StudyRoomSeatQueryClientReqDTO form, Long userId);

    /** 提交预约（v2）。 */
    StudyRoomReserveConfirmClientRespDTO reserveV2SeatConfirm(StudyRoomReserveConfirmClientReqDTO form);

    /** 提交预约（v2，指定用户）。 */
    StudyRoomReserveConfirmClientRespDTO reserveV2SeatConfirm(
            StudyRoomReserveConfirmClientReqDTO form, Long userId);

    /** 座位图（v1）。 */
    StudyRoomSeatMapClientRespDTO reserveSeatList(StudyRoomSeatQueryV1ClientReqDTO form);

    /** 提交预约（v1）。 */
    StudyRoomReserveConfirmClientRespDTO reserveSeatConfirm(StudyRoomReserveConfirmV1ClientReqDTO form);

    /** 是否还能再预约一个座位。 */
    StudyRoomReserveAnotherCheckClientRespDTO reserveAnotherCheck(Long userReserveId);

    /** 我的预约列表（指定用户）。 */
    StudyRoomUserReserveListClientRespDTO userReserveList(Long userId);

    /** 取消预约前的提示。 */
    StudyRoomUserReserveCancelTipClientRespDTO userReserveCancelTip(Long userReserveId);

    /** 取消预约。 */
    Integer userReserveCancel(StudyRoomUserReserveCancelClientReqDTO form);

    /** 我的预约状态选项，取自 {@code StudyRoomUserReserveStatusEnum}。 */
    List<String> userReserveStatusList();

    /** 我的预约列表，按状态在服务层筛选：statusDesc 为空时剔除已取消（含爽约系统取消），指定时只保留该状态。 */
    StudyRoomUserReserveListClientRespDTO userReserveList(String statusDesc);
}
