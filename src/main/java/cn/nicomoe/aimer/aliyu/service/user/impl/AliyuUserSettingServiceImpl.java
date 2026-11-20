package cn.nicomoe.aimer.aliyu.service.user.impl;

import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserSettingConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserSettingReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.LoginUserRespDTO;
import cn.nicomoe.aimer.aliyu.model.entity.AliyuUserSettingDO;
import cn.nicomoe.aimer.aliyu.repository.AliyuUserSettingRepository;
import cn.nicomoe.aimer.aliyu.service.client.AliyuUserService;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 阿里鱼用户设置服务实现。
 */
@Service
@RequiredArgsConstructor
public class AliyuUserSettingServiceImpl implements AliyuUserSettingService {

    private final AliyuUserSettingRepository settingRepository;
    private final AliyuUserService userService;

    @Override
    public AliyuUserConfigDTO get(Long userId) {
        AliyuUserSettingDO existDO = settingRepository.findFirstByUserId(userId);
        if (Objects.isNull(existDO)) {
            AliyuUserConfigDTO configDTO = new AliyuUserConfigDTO();
            configDTO.setUserId(userId);
            configDTO.setConfig(this.defaultConfig());
            return configDTO;
        }
        AliyuUserConfigDTO configDTO = new AliyuUserConfigDTO();
        configDTO.setUserId(existDO.getUserId());
        configDTO.setConfig(GsonUtils.fromJson(existDO.getConfigJson(), AliyuUserSettingConfigBO.class));
        return configDTO;
    }

    @Override
    public void update(Long userId, AliyuUserSettingReqDTO req) {
        LoginUserRespDTO loginUserRespDTO = userService.loginUser(req.getCookie());
        CheckUtils.throwIfNull(loginUserRespDTO, AliyuApiErrorCodeEnum.CREDENTIAL_INVALID.getDescription());

        AliyuUserSettingConfigBO configBO = MapstructUtils.convert(req, AliyuUserSettingConfigBO.class);
        AliyuUserSettingDO existDO = settingRepository.findFirstByUserId(userId);
        if (Objects.isNull(existDO)) {
            AliyuUserSettingDO settingDO = new AliyuUserSettingDO();
            settingDO.setUserId(userId);
            settingDO.setConfigJson(GsonUtils.toJson(configBO));
            settingRepository.save(settingDO);
        } else {
            existDO.setConfigJson(GsonUtils.toJson(configBO));
            settingRepository.save(existDO);
        }
    }

    private AliyuUserSettingConfigBO defaultConfig() {
        return new AliyuUserSettingConfigBO();
    }
}
