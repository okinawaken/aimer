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
import java.util.List;
import java.util.Objects;

/**
 * 已签到预约到期校验过滤器
 * <p>
 * 取今日状态为「已签到」、且预约时段的结束时刻已到达的预约，作为自动签退的触发依据。
 *
 * @author bixiu
 * @since 4.2.0
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

        List<StudyRoomUserReserveClientRespDTO> expiredReserveList = userReserveListClientRespDTO.getList().stream()
                .filter(reserve -> Objects.equals(StudyRoomUserReserveStatusEnum.SIGNED.getValue(), reserve.getReserveStatusDesc()))
                .filter(reserve -> Objects.equals(reserve.getReserveDateStr(), LocalDate.now()))
                .filter(this::isReserveEnded)
                .toList();
        if (expiredReserveList.isEmpty()) {
            return FilterResultDTO.fail("当前无已到期的已签到预约");
        }
        context.setExpiredReserveClientRespDTO(expiredReserveList.get(0));
        return FilterResultDTO.success(expiredReserveList);
    }

    /**
     * 预约时段的结束时刻是否已到达。
     *
     * @param reserve 预约记录
     * @return 是否已到期
     */
    private boolean isReserveEnded(StudyRoomUserReserveClientRespDTO reserve) {
        String reserveTimeStr = reserve.getReserveTimeStr();
        if (StrUtil.isBlank(reserveTimeStr)) {
            return false;
        }
        String[] reserveTimeStrArray = StrUtil.splitToArray(reserveTimeStr, '-');
        if (reserveTimeStrArray.length < 2) {
            return false;
        }
        LocalTime endTime = LocalTime.parse(reserveTimeStrArray[1]);
        return !LocalTime.now().isBefore(endTime);
    }
}
