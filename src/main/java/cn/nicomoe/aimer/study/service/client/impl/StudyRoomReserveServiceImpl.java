package cn.nicomoe.aimer.study.service.client.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.client.http.v1.StudyRoomReserveClient;
import cn.nicomoe.aimer.study.client.http.v2.StudyRoomReserveV2Client;
import cn.nicomoe.aimer.study.enums.StudyRoomUserReserveStatusEnum;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
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
import cn.nicomoe.aimer.study.service.client.StudyRoomReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 自习室预约域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomReserveServiceImpl implements StudyRoomReserveService {

    private final StudyRoomReserveClient reserveClient;
    private final StudyRoomReserveV2Client reserveV2Client;

    @Override
    public StudyRoomReserveCheckClientRespDTO reserveCheck() {
        StudyRoomClientResult<StudyRoomReserveCheckClientRespDTO> result = reserveClient.reserveCheck();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomReserveCheckClientRespDTO reserveCheck(Long userId) {
        StudyRoomClientResult<StudyRoomReserveCheckClientRespDTO> result = reserveV2Client.reserveCheck(userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomReserveDateClientRespDTO reserveDateList(Integer venueId) {
        StudyRoomClientResult<StudyRoomReserveDateClientRespDTO> result = reserveClient.reserveDateList(venueId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomReserveDateClientRespDTO reserveDateList(Integer venueId, Long userId) {
        StudyRoomClientResult<StudyRoomReserveDateClientRespDTO> result = reserveV2Client.reserveDateList(venueId, userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSeatMapClientRespDTO reserveV2SeatList(StudyRoomSeatQueryClientReqDTO form) {
        StudyRoomClientResult<StudyRoomSeatMapClientRespDTO> result = reserveClient.reserveV2SeatList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSeatMapClientRespDTO reserveV2SeatList(StudyRoomSeatQueryClientReqDTO form, Long userId) {
        StudyRoomClientResult<StudyRoomSeatMapClientRespDTO> result = reserveV2Client.reserveV2SeatList(form, userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomReserveConfirmClientRespDTO reserveV2SeatConfirm(StudyRoomReserveConfirmClientReqDTO form) {
        StudyRoomClientResult<StudyRoomReserveConfirmClientRespDTO> result = reserveClient.reserveV2SeatConfirm(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomReserveConfirmClientRespDTO reserveV2SeatConfirm(
            StudyRoomReserveConfirmClientReqDTO form, Long userId) {
        StudyRoomClientResult<StudyRoomReserveConfirmClientRespDTO> result = reserveV2Client.reserveV2SeatConfirm(form, userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSeatMapClientRespDTO reserveSeatList(StudyRoomSeatQueryV1ClientReqDTO form) {
        StudyRoomClientResult<StudyRoomSeatMapClientRespDTO> result = reserveClient.reserveSeatList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomReserveConfirmClientRespDTO reserveSeatConfirm(StudyRoomReserveConfirmV1ClientReqDTO form) {
        StudyRoomClientResult<StudyRoomReserveConfirmClientRespDTO> result = reserveClient.reserveSeatConfirm(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomReserveAnotherCheckClientRespDTO reserveAnotherCheck(Long userReserveId) {
        StudyRoomClientResult<StudyRoomReserveAnotherCheckClientRespDTO> result = reserveClient.reserveAnotherCheck(userReserveId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomUserReserveListClientRespDTO userReserveList(Long userId) {
        StudyRoomClientResult<StudyRoomUserReserveListClientRespDTO> result = reserveV2Client.userReserveList(userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomUserReserveCancelTipClientRespDTO userReserveCancelTip(Long userReserveId) {
        StudyRoomClientResult<StudyRoomUserReserveCancelTipClientRespDTO> result = reserveClient.userReserveCancelTip(userReserveId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Integer userReserveCancel(StudyRoomUserReserveCancelClientReqDTO form) {
        StudyRoomClientResult<Integer> result = reserveClient.userReserveCancel(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<String> userReserveStatusList() {
        return Arrays.stream(StudyRoomUserReserveStatusEnum.values())
                .map(StudyRoomUserReserveStatusEnum::getDescription)
                .toList();
    }

    @Override
    public StudyRoomUserReserveListClientRespDTO userReserveList(String statusDesc) {
        StudyRoomClientResult<StudyRoomUserReserveListClientRespDTO> result = reserveClient.userReserveList();
        if (Objects.isNull(result) || Objects.isNull(result.getData())) {
            return null;
        }
        StudyRoomUserReserveListClientRespDTO reserveListDTO = result.getData();
        reserveListDTO.setList(this.filterByStatusDesc(reserveListDTO.getList(), statusDesc));
        return reserveListDTO;
    }

    /**
     * 按状态描述筛选预约记录：状态为空时剔除已取消（含爽约系统取消），否则只保留匹配描述的记录。
     *
     * @param reserveList 上游返回的全部预约记录
     * @param statusDesc  状态描述
     * @return 筛选后的预约记录
     */
    private List<StudyRoomUserReserveClientRespDTO> filterByStatusDesc(List<StudyRoomUserReserveClientRespDTO> reserveList, String statusDesc) {
        if (CollUtil.isEmpty(reserveList)) {
            return reserveList;
        }
        if (StrUtil.isBlank(statusDesc)) {
            return reserveList.stream()
                    .filter(reserve -> !StudyRoomUserReserveStatusEnum.isCancelled(reserve.getReserveStatusDesc()))
                    .toList();
        }
        return reserveList.stream()
                .filter(reserve -> Objects.equals(statusDesc, reserve.getReserveStatusDesc()))
                .toList();
    }
}
