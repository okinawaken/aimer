package cn.nicomoe.aimer.study.scheduler;

import cn.hutool.core.collection.CollUtil;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveTriggerTypeEnum;
import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.model.event.StudyRoomAutoReserveExecuteEvent;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskRecordService;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskService;
import cn.nicomoe.aimer.study.service.user.StudyRoomUserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * 自动预约调度器。
 * <p>
 * 每分钟整点扫描所有启用中的任务：用户放号时刻已过、且当天尚未执行过，才发布执行事件。
 * 执行器监听事件并异步运行，扫描本身不等待结果；重启后只要当天还没落执行记录，仍会补跑。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoReserveScheduler {

    private final StudyRoomAutoReserveTaskService taskService;
    private final StudyRoomUserSettingService settingService;
    private final StudyRoomAutoReserveTaskRecordService recordService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 扫描启用中的任务并发布执行事件。
     */
    @Scheduled(cron = "0 * * * * ?")
    public void scan() {
        List<StudyRoomAutoReserveTaskInfoDTO> taskInfoDTOList = taskService.listEnabledTasks();
        for (StudyRoomAutoReserveTaskInfoDTO taskInfoDTO : taskInfoDTOList) {
            LocalDate nowDate = LocalDate.now();
            LocalTime nowTime = LocalTime.now();
            StudyRoomUserConfigDTO userConfigDTO = settingService.get(taskInfoDTO.getUserId());
            if (Objects.isNull(userConfigDTO) || Objects.isNull(userConfigDTO.getConfig())) {
                continue;
            }
            StudyRoomUserConfigBO configBO = userConfigDTO.getConfig();
            if (Objects.isNull(configBO.getReleaseTime()) || nowTime.isBefore(configBO.getReleaseTime())) {
                continue;
            }
            List<StudyRoomAutoReserveTaskRecordInfoDTO> recordDTOList = recordService.listByTaskIdAndCreateDate(taskInfoDTO.getId(), nowDate);
            if (CollUtil.isEmpty(recordDTOList)) {
                StudyRoomAutoReserveExecuteEvent event = new StudyRoomAutoReserveExecuteEvent(taskInfoDTO.getId(), StudyRoomAutoReserveTriggerTypeEnum.SCHEDULED.getValue());
                eventPublisher.publishEvent(event);
            }
        }
    }
}
