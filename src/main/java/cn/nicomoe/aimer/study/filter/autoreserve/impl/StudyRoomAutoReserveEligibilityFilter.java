package cn.nicomoe.aimer.study.filter.autoreserve.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveFilterOrderEnum;
import cn.nicomoe.aimer.study.enums.StudyRoomReserveStatusEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autoreserve.StudyRoomAutoReserveFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoReserveContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveCheckClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomReserveService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 预约资格校验过滤器
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoReserveEligibilityFilter implements StudyRoomAutoReserveFilter {

    private final StudyRoomReserveService reserveService;

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoReserveContext context) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = context.getTaskInfoDTO();
        StudyRoomReserveCheckClientRespDTO reserveCheckClientRespDTO = reserveService.reserveCheck(taskInfoDTO.getUserId());
        CheckUtils.throwIfNull(reserveCheckClientRespDTO, "预约资格返回为空");

        if (!StudyRoomReserveStatusEnum.isCanReserve(reserveCheckClientRespDTO.getReserveState())) {
            return FilterResultDTO.fail("无预约资格");
        }
        return FilterResultDTO.success(reserveCheckClientRespDTO);
    }

    @Override
    public int getOrder() {
        return StudyRoomAutoReserveFilterOrderEnum.ELIGIBILITY.getValue();
    }
}
