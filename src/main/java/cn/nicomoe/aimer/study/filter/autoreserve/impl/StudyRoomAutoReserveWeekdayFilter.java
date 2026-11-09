package cn.nicomoe.aimer.study.filter.autoreserve.impl;

import cn.hutool.core.collection.CollUtil;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autoreserve.StudyRoomAutoReserveFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoReserveContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 执行星期过滤器
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoReserveWeekdayFilter implements StudyRoomAutoReserveFilter {

    @Override
    public int getOrder() {
        return StudyRoomAutoReserveFilterOrderEnum.WEEKDAY.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoReserveContext context) {
        LocalDate targetDate = context.getTargetDate();
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = context.getTaskInfoDTO();
        if (!CollUtil.contains(taskInfoDTO.getWeekDays(), targetDate.getDayOfWeek().getValue())) {
            return FilterResultDTO.fail("目标日期不在执行计划内");
        }
        return FilterResultDTO.success(targetDate);
    }
}
