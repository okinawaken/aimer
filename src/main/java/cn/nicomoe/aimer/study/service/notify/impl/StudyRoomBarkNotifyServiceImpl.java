package cn.nicomoe.aimer.study.service.notify.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.notify.client.http.BarkClient;
import cn.nicomoe.aimer.notify.model.dto.BarkPushReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.service.notify.StudyRoomBarkNotifyService;
import cn.nicomoe.aimer.study.service.user.StudyRoomUserSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自习室 Bark 通知服务实现。
 * <p>按 userId 解析用户配置中的 Bark 推送地址，通过 {@link BarkClient} 推送；
 * 未配置或推送异常均不影响调用方主流程。</p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudyRoomBarkNotifyServiceImpl implements StudyRoomBarkNotifyService {

    private final BarkClient barkClient;
    private final StudyRoomUserSettingService settingService;

    private static final String DEFAULT_GROUP = "自习室";

    @Override
    public void send(Long userId, String title, String body) {
        StudyRoomUserConfigDTO configDTO = settingService.get(userId);
        if (Objects.isNull(configDTO)
                || Objects.isNull(configDTO.getConfig())
                || StrUtil.isEmpty(configDTO.getConfig().getBarkUrl())) {
            return;
        }
        String barkUrl = configDTO.getConfig().getBarkUrl();

        BarkPushReqDTO payload = new BarkPushReqDTO();
        payload.setTitle(title);
        payload.setBody(body);
        payload.setGroup(DEFAULT_GROUP);
        try {
            barkClient.push(barkUrl, payload);
        } catch (Exception e) {
            log.warn("Bark 推送失败，url={}，title={}，原因={}", barkUrl, title, e.getMessage());
        }
    }
}
