package cn.nicomoe.aimer.study.scheduler;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignOutEnableEnum;
import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.model.event.StudyRoomAutoSignOutExecuteEvent;
import cn.nicomoe.aimer.study.service.user.StudyRoomUserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 自动签退调度器。
 * <p>
 * 扫描已开启自动签退的用户并发布执行事件，具体到期判断与签退在过滤器和执行器中完成。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignOutScheduler {

    private final StudyRoomUserSettingService settingService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 扫描已开启自动签退的用户并发布执行事件。
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void scan() {
        List<StudyRoomUserConfigDTO> userConfigDTOList = settingService.listAllConfigs();
        for (StudyRoomUserConfigDTO userConfigDTO : userConfigDTOList) {
            StudyRoomUserConfigBO configBO = userConfigDTO.getConfig();
            if (Objects.isNull(configBO)) {
                continue;
            }
            if (StudyRoomAutoSignOutEnableEnum.isEnabled(configBO.getSignOutEnable())) {
                StudyRoomAutoSignOutExecuteEvent event = new StudyRoomAutoSignOutExecuteEvent(userConfigDTO.getUserId());
                eventPublisher.publishEvent(event);
            }
        }
    }
}
