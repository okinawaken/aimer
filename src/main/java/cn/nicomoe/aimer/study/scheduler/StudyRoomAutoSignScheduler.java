package cn.nicomoe.aimer.study.scheduler;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignInEnableEnum;
import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.model.event.StudyRoomAutoSignExecuteEvent;
import cn.nicomoe.aimer.study.service.user.StudyRoomUserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 自动签到调度器。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignScheduler {

    private final StudyRoomUserSettingService settingService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 扫描已开启自动签到的用户并发布执行事件。
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void scan() {
        List<StudyRoomUserConfigDTO> userConfigDTOList = settingService.listAllConfigs();
        for (StudyRoomUserConfigDTO userConfigDTO : userConfigDTOList) {
            StudyRoomUserConfigBO configBO = userConfigDTO.getConfig();
            if (Objects.isNull(configBO)) {
                continue;
            }
            if (StudyRoomAutoSignInEnableEnum.isEnabled(configBO.getSignInEnable())) {
                StudyRoomAutoSignExecuteEvent event = new StudyRoomAutoSignExecuteEvent(userConfigDTO.getUserId());
                eventPublisher.publishEvent(event);
            }
        }
    }
}
