package cn.nicomoe.aimer.study.filter.autoreserve.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveFilterOrderEnum;
import cn.nicomoe.aimer.study.enums.StudyRoomSeatStatusEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autoreserve.StudyRoomAutoReserveFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoReserveContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatAreaClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatMapClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatQueryClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatValidTimeClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomReserveService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 座位图查询与选座过滤器
 * <p>
 * 选座优先级：指定座位 → 偏好区域首个可约座位 → 全场首个可约座位 → 失败。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoReserveSeatFilter implements StudyRoomAutoReserveFilter {

    private final StudyRoomReserveService reserveService;

    @Override
    public int getOrder() {
        return StudyRoomAutoReserveFilterOrderEnum.SEAT.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoReserveContext context) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = context.getTaskInfoDTO();
        LocalDate targetDate = context.getTargetDate();

        StudyRoomSeatQueryClientReqDTO seatQueryClientReq = new StudyRoomSeatQueryClientReqDTO();
        seatQueryClientReq.setVenueId(taskInfoDTO.getVenueId());
        seatQueryClientReq.setReserveDateStr(targetDate);
        seatQueryClientReq.setReserveStartTimeStr(taskInfoDTO.getReserveStartTime());
        seatQueryClientReq.setReserveEndTimeStr(taskInfoDTO.getReserveEndTime());
        seatQueryClientReq.setReserveAnotherFlag(0);
        StudyRoomSeatMapClientRespDTO seatMapClientRespDTO = reserveService.reserveV2SeatList(seatQueryClientReq, taskInfoDTO.getUserId());
        CheckUtils.throwIfNull(seatMapClientRespDTO, "座位图返回为空");

        StudyRoomSeatClientRespDTO seatClientRespDTO = this.selectSeat(context, seatMapClientRespDTO);
        context.setSeatClientRespDTO(seatClientRespDTO);
        if (Objects.isNull(seatClientRespDTO)) {
            return FilterResultDTO.fail("期望时段无可预约座位");
        }
        return FilterResultDTO.success(seatClientRespDTO);
    }

    private StudyRoomSeatClientRespDTO selectSeat(StudyRoomAutoReserveContext context,
                                                  StudyRoomSeatMapClientRespDTO seatMapClientRespDTO) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = context.getTaskInfoDTO();
        StudyRoomSeatClientRespDTO areaSeatClientRespDTO = null;
        StudyRoomSeatClientRespDTO anySeatClientRespDTO = null;

        for (StudyRoomSeatAreaClientRespDTO seatAreaClientRespDTO : seatMapClientRespDTO
                .getVenueAreaList()) {
            boolean isPreferredArea =
                    Objects.equals(seatAreaClientRespDTO.getId(), taskInfoDTO.getAreaId());
            for (StudyRoomSeatClientRespDTO seatClientRespDTO : seatAreaClientRespDTO
                    .getVenueAreaSeatList()) {
                boolean seatAvailable = this.isSeatAvailable(seatClientRespDTO,
                        taskInfoDTO.getReserveStartTime(), taskInfoDTO.getReserveEndTime());
                if (!seatAvailable) {
                    continue;
                }
                if (Objects.equals(seatClientRespDTO.getId(), taskInfoDTO.getSeatId())) {
                    return seatClientRespDTO;
                }
                if (Objects.isNull(areaSeatClientRespDTO) && isPreferredArea) {
                    areaSeatClientRespDTO = seatClientRespDTO;
                }
                if (Objects.isNull(anySeatClientRespDTO)) {
                    anySeatClientRespDTO = seatClientRespDTO;
                }
            }
        }
        return Objects.nonNull(areaSeatClientRespDTO) ? areaSeatClientRespDTO : anySeatClientRespDTO;
    }

    private boolean isSeatAvailable(StudyRoomSeatClientRespDTO seatClientRespDTO, LocalTime startTime,
                                    LocalTime endTime) {
        if (!StudyRoomSeatStatusEnum.isCanReserve(seatClientRespDTO.getSeatReserveState())) {
            return false;
        }

        for (StudyRoomSeatValidTimeClientRespDTO validTime : seatClientRespDTO.getValidTimeStrList()) {
            if (!startTime.isBefore(validTime.getValidStartTimeStr())
                    && !endTime.isAfter(validTime.getValidEndTimeStr())) {
                return true;
            }
        }
        return false;
    }
}
