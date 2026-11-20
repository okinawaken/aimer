package cn.nicomoe.aimer.study.service.notify.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.notify.model.dto.BarkPushReqDTO;
import cn.nicomoe.aimer.notify.service.BarkService;
import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.service.notify.StudyRoomBarkNotifyService;
import cn.nicomoe.aimer.study.service.user.StudyRoomUserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 按 userId 取用户配置里的 Bark 地址推送；未配置时静默跳过，地址校验与失败处理由 {@link BarkService} 负责。
 */
@Service
@RequiredArgsConstructor
public class StudyRoomBarkNotifyServiceImpl implements StudyRoomBarkNotifyService {

    private static final String DEFAULT_GROUP = "自习室";

    private final BarkService barkService;
    private final StudyRoomUserSettingService settingService;

    @Override
    public void send(Long userId, String title, String body) {
        StudyRoomUserConfigDTO configDTO = settingService.get(userId);
        String barkUrl = Optional.ofNullable(configDTO).map(StudyRoomUserConfigDTO::getConfig)
                .map(StudyRoomUserConfigBO::getBarkUrl)
                .orElse(null);
        if (StrUtil.isEmpty(barkUrl)) {
            return;
        }

        BarkPushReqDTO payload = new BarkPushReqDTO();
        payload.setTitle(title);
        payload.setBody(body);
        payload.setGroup(DEFAULT_GROUP);
        barkService.push(barkUrl, payload);
    }
}
