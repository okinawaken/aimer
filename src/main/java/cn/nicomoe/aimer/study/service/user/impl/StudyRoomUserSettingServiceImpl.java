package cn.nicomoe.aimer.study.service.user.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignInEnableEnum;
import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSettingReqDTO;
import cn.nicomoe.aimer.study.model.entity.StudyRoomUserSettingDO;
import cn.nicomoe.aimer.study.repository.StudyRoomUserSettingRepository;
import cn.nicomoe.aimer.study.service.client.StudyRoomUserService;
import cn.nicomoe.aimer.study.service.user.StudyRoomUserSettingService;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * 自习室用户设置服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomUserSettingServiceImpl implements StudyRoomUserSettingService {

    private final StudyRoomUserSettingRepository settingRepository;
    private final StudyRoomUserService userService;

    @Override
    public StudyRoomUserConfigDTO get(Long userId) {
        StudyRoomUserSettingDO settingDO = settingRepository.findFirstByUserId(userId);
        if (Objects.isNull(settingDO)) {
            StudyRoomUserConfigDTO userConfigDTO = new StudyRoomUserConfigDTO();
            userConfigDTO.setUserId(userId);
            userConfigDTO.setConfig(this.defaultConfig());
            return userConfigDTO;
        }
        StudyRoomUserConfigDTO userConfigDTO = new StudyRoomUserConfigDTO();
        userConfigDTO.setUserId(settingDO.getUserId());
        userConfigDTO.setConfig(GsonUtils.fromJson(settingDO.getConfigJson(), StudyRoomUserConfigBO.class));
        return userConfigDTO;
    }

    @Override
    public List<StudyRoomUserConfigDTO> listAllConfigs() {
        return settingRepository.findAll().stream()
                .map(settingDO -> {
                    StudyRoomUserConfigDTO userConfigDTO = new StudyRoomUserConfigDTO();
                    userConfigDTO.setUserId(settingDO.getUserId());
                    userConfigDTO.setConfig(GsonUtils.fromJson(settingDO.getConfigJson(), StudyRoomUserConfigBO.class));
                    return userConfigDTO;
                })
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudyRoomUserSettingReqDTO req, Long userId) {
        userService.userDetail(req.getToken());
        StudyRoomUserConfigBO configBO = MapstructUtils.convert(req, StudyRoomUserConfigBO.class);

        StudyRoomUserSettingDO existDO = settingRepository.findFirstByUserId(userId);
        if (Objects.isNull(existDO)) {
            StudyRoomUserSettingDO settingDO = new StudyRoomUserSettingDO();
            settingDO.setUserId(userId);
            settingDO.setConfigJson(GsonUtils.toJson(configBO));
            settingRepository.save(settingDO);
        } else {
            existDO.setConfigJson(GsonUtils.toJson(configBO));
            settingRepository.save(existDO);
        }
    }

    /**
     * 构建默认用户配置。
     *
     * @return 默认配置
     */
    private StudyRoomUserConfigBO defaultConfig() {
        StudyRoomUserConfigBO configBO = new StudyRoomUserConfigBO();
        configBO.setReleaseTime(LocalTime.of(20, 0, 0));
        configBO.setSignInEnable(StudyRoomAutoSignInEnableEnum.DISABLED.getValue());
        configBO.setToken(StrUtil.EMPTY);
        return configBO;
    }
}
