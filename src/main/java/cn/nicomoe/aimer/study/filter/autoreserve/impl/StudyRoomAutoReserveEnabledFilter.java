package cn.nicomoe.aimer.study.filter.autoreserve.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveFilterOrderEnum;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveTaskEnableStatusEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autoreserve.StudyRoomAutoReserveFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoReserveContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 任务已暂停则终止链路并跳过（仍会写一条跳过记录）。 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoReserveEnabledFilter implements StudyRoomAutoReserveFilter {

    @Override
    public int getOrder() {
        return StudyRoomAutoReserveFilterOrderEnum.ENABLED.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoReserveContext context) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = context.getTaskInfoDTO();
        if (!StudyRoomAutoReserveTaskEnableStatusEnum.isEnabled(taskInfoDTO.getEnableStatus())) {
            return FilterResultDTO.fail("任务已暂停");
        }
        return FilterResultDTO.success(taskInfoDTO);
    }
}
