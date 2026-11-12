package cn.nicomoe.aimer.study.filter.autoreserve.impl;

import cn.hutool.core.collection.CollUtil;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autoreserve.StudyRoomAutoReserveFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoReserveContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveDateClientRespDTO;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskRecordService;
import cn.nicomoe.aimer.study.service.client.StudyRoomReserveService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 目标日期与放号基线过滤器
 * <p>
 * 计算可预约的最大目标日期，并校验当日是否已执行、是否放出新日期。
 * 合并自原 {@code TARGET_DATE} 与 {@code EXECUTED} 两步校验，因已执行校验依赖目标日期计算结果。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoReserveTargetDateFilter implements StudyRoomAutoReserveFilter {

    private static final Pattern DATE_PREFIX_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}");

    private final StudyRoomReserveService reserveService;

    private final StudyRoomAutoReserveTaskRecordService recordService;

    @Override
    public int getOrder() {
        return StudyRoomAutoReserveFilterOrderEnum.TARGET_DATE.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoReserveContext context) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = context.getTaskInfoDTO();
        StudyRoomReserveDateClientRespDTO reserveDateClientRespDTO = reserveService.reserveDateList(taskInfoDTO.getVenueId(), taskInfoDTO.getUserId());
        CheckUtils.throwIfNull(reserveDateClientRespDTO, "预约日期返回为空");

        LocalDate targetDate = this.resolveMaxReserveDate(reserveDateClientRespDTO);
        context.setTargetDate(targetDate);
        if (Objects.isNull(targetDate)) {
            return FilterResultDTO.fail("可预约日期列表中无有效日期");
        }
        List<StudyRoomAutoReserveTaskRecordInfoDTO> taskRecordInfoRespList = recordService.listByTaskIdAndTargetDate(taskInfoDTO.getId(), targetDate);
        if (CollUtil.isNotEmpty(taskRecordInfoRespList)) {
            return FilterResultDTO.fail("今日已执行");
        }
        LocalDate lastMaxReserveDate = taskInfoDTO.getLastMaxReserveDate();
        if (Objects.nonNull(lastMaxReserveDate) && !targetDate.isAfter(lastMaxReserveDate)) {
            return FilterResultDTO.fail("未放出新日期的预约");
        }
        return FilterResultDTO.success(targetDate);
    }

    private LocalDate resolveMaxReserveDate(StudyRoomReserveDateClientRespDTO reserveDateClientRespDTO) {
        LocalDate maxDate = null;
        for (String reserveDate : reserveDateClientRespDTO.getReserveDateList()) {
            Matcher matcher = DATE_PREFIX_PATTERN.matcher(reserveDate);
            if (!matcher.find()) {
                continue;
            }
            LocalDate date = LocalDate.parse(matcher.group());
            if (Objects.isNull(maxDate) || date.isAfter(maxDate)) {
                maxDate = date;
            }
        }
        return maxDate;
    }
}
