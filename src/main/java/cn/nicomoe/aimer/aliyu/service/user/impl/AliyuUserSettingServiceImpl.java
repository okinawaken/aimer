package cn.nicomoe.aimer.aliyu.service.user.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.client.http.v3.AliyuUserV3Client;
import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserSettingReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.LoginUserRespDTO;
import cn.nicomoe.aimer.aliyu.model.entity.AliyuUserSettingDO;
import cn.nicomoe.aimer.aliyu.repository.AliyuUserSettingRepository;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/** 阿里鱼用户设置服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuUserSettingServiceImpl implements AliyuUserSettingService {

    private final AliyuUserV3Client userV3Client;
    private final AliyuUserSettingRepository settingRepository;

    @Override
    public AliyuUserConfigDTO get(Long userId) {
        AliyuUserSettingDO settingDO = settingRepository.findFirstByUserId(userId);
        if (Objects.isNull(settingDO)) {
            AliyuUserConfigDTO userConfigDTO = new AliyuUserConfigDTO();
            userConfigDTO.setUserId(userId);
            userConfigDTO.setConfig(this.defaultConfig());
            return userConfigDTO;
        }
        AliyuUserConfigDTO userConfigDTO = new AliyuUserConfigDTO();
        userConfigDTO.setUserId(settingDO.getUserId());
        userConfigDTO.setConfig(GsonUtils.fromJson(settingDO.getConfigJson(), AliyuUserConfigBO.class));
        return userConfigDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long userId, AliyuUserSettingReqDTO req) {
        this.verifyCredential(req);

        AliyuUserConfigBO configBO = MapstructUtils.convert(req, AliyuUserConfigBO.class);
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

    private AliyuUserConfigBO defaultConfig() {
        return new AliyuUserConfigBO();
    }

    private void verifyCredential(AliyuUserSettingReqDTO req) {
        CheckUtils.throwIf(StrUtil.isBlank(req.getCookie()), AliyuApiErrorCodeEnum.CREDENTIAL_MISSING.getDescription());
        CheckUtils.throwIf(StrUtil.isBlank(req.getSessValue()), AliyuApiErrorCodeEnum.SESSION_MISSING.getDescription());

        AliyuClientResult<LoginUserRespDTO> result = userV3Client.loginUser(req.getCookie(), req.getSessValue());
        CheckUtils.throwIf(Objects.isNull(result) || Objects.isNull(result.getData()), AliyuApiErrorCodeEnum.CREDENTIAL_INVALID.getDescription());
    }
}
