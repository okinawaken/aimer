package cn.nicomoe.aimer.study.filter.autosignout.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignOutFilterOrderEnum;
import cn.nicomoe.aimer.study.enums.StudyRoomUserReserveStatusEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosignout.StudyRoomAutoSignOutFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
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
 * 已签到预约到期校验过滤器
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignOutSignedReserveFilter implements StudyRoomAutoSignOutFilter {

    private final StudyRoomReserveService reserveService;

    @Override
    public int getOrder() {
        return StudyRoomAutoSignOutFilterOrderEnum.SIGNED_RESERVE_END.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoSignOutContext context) {
        StudyRoomUserReserveListClientRespDTO userReserveListClientRespDTO = reserveService.userReserveList(context.getUserId());
        CheckUtils.throwIfNull(userReserveListClientRespDTO, "我的预约返回为空");

        StudyRoomUserReserveClientRespDTO expireReserveClientRespDTO = userReserveListClientRespDTO.getList()
                .stream()
                .filter(reserve -> Objects.equals(StudyRoomUserReserveStatusEnum.SIGNED.getValue(), reserve.getReserveStatusDesc()))
                .filter(reserve -> Objects.equals(reserve.getReserveDateStr(), LocalDate.now()))
                .filter(reserve -> {
                    String[] reserveTimeStrArray = StrUtil.splitToArray(reserve.getReserveTimeStr(), '-');
                    LocalTime endTime = LocalTime.parse(reserveTimeStrArray[1]);
                    return LocalTime.now().isAfter(endTime);
                })
                .findFirst()
                .orElse(null);
        context.setExpiredReserveClientRespDTO(expireReserveClientRespDTO);
        if (Objects.isNull(expireReserveClientRespDTO)) {
            return FilterResultDTO.fail("当前无已到期的已签到预约");
        }
        return FilterResultDTO.success(expireReserveClientRespDTO);
    }
}
