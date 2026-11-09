package cn.nicomoe.aimer.study.filter.autoreserve.impl;

import cn.hutool.core.collection.CollUtil;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autoreserve.StudyRoomAutoReserveFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoReserveContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * 今日已执行过滤器
 * <p>
 * 当天已有执行记录时终止链路并返回跳过结果；执行器仍会写入一条新的跳过记录。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoReserveExecutedFilter implements StudyRoomAutoReserveFilter {

    private final StudyRoomAutoReserveTaskRecordService recordService;

    @Override
    public int getOrder() {
        return StudyRoomAutoReserveFilterOrderEnum.EXECUTED.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoReserveContext context) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = context.getTaskInfoDTO();
        List<StudyRoomAutoReserveTaskRecordInfoDTO> taskRecordInfoRespList = recordService.listByTaskIdAndCreateDate(taskInfoDTO.getId(), LocalDate.now());
        if (CollUtil.isNotEmpty(taskRecordInfoRespList)) {
            return FilterResultDTO.fail("今日已执行");
        }
        return FilterResultDTO.success(taskRecordInfoRespList);
    }
}
