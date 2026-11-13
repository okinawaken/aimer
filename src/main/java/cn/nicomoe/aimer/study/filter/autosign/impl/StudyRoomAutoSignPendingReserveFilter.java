package cn.nicomoe.aimer.study.filter.autosign.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignFilterOrderEnum;
import cn.nicomoe.aimer.study.enums.StudyRoomUserReserveStatusEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosign.StudyRoomAutoSignFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveListClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomReserveService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 待签到预约查询过滤器
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignPendingReserveFilter implements StudyRoomAutoSignFilter {

    private final StudyRoomReserveService reserveService;

    @Override
    public int getOrder() {
        return StudyRoomAutoSignFilterOrderEnum.PENDING_RESERVE.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoSignContext context) {
        StudyRoomUserReserveClientRespDTO userReserveClientRespDTO = this.findPendingReserve(context.getUserId());
        context.setReserveClientRespDTO(userReserveClientRespDTO);
        if (Objects.isNull(userReserveClientRespDTO)) {
            return FilterResultDTO.fail("当前无待签到预约");
        }
        return FilterResultDTO.success(userReserveClientRespDTO);
    }

    private StudyRoomUserReserveClientRespDTO findPendingReserve(Long userId) {
        StudyRoomUserReserveListClientRespDTO userReserveListClientRespDTO = reserveService.userReserveList(userId);
        CheckUtils.throwIfNull(userReserveListClientRespDTO, "我的预约返回为空");
        return userReserveListClientRespDTO.getList().stream()
                .filter(reserve -> Objects.equals(reserve.getReserveStatusDesc(), StudyRoomUserReserveStatusEnum.PENDING_SIGN.getValue()))
                .filter(reserve -> Objects.equals(reserve.getReserveDateStr(), LocalDate.now()))
                .filter(reserve -> {
                    LocalTime currentTime = LocalTime.now();
                    String[] reserveTimeStrArray = StrUtil.splitToArray(reserve.getReserveTimeStr(), '-');
                    LocalTime beginTime = LocalTime.parse(reserveTimeStrArray[0]);
                    LocalTime endTime = LocalTime.parse(reserveTimeStrArray[1]);
                    return !currentTime.isBefore(beginTime) && !currentTime.isAfter(endTime);
                })
                .findFirst()
                .orElse(null);
    }
}
