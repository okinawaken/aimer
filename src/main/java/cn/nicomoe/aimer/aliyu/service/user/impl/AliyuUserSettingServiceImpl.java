package cn.nicomoe.aimer.aliyu.service.user.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.client.http.AliyuCredentialClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserSettingReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.LoginUserRespDTO;
import cn.nicomoe.aimer.aliyu.model.entity.AliyuUserSettingDO;
import cn.nicomoe.aimer.aliyu.repository.AliyuUserSettingRepository;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import java.util.Objects;

/**
 * 阿里鱼用户设置服务实现。
 *
 * @author bixiu
 * @since 4.3.0
 */
@Service
@RequiredArgsConstructor
public class AliyuUserSettingServiceImpl implements AliyuUserSettingService {

    private final AliyuUserSettingRepository settingRepository;
    private final AliyuCredentialClient credentialClient;

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
    public void update(AliyuUserSettingReqDTO req, Long userId) {
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

    /**
     * 构建默认用户配置。
     * <p>
     * 未绑定状态返回空配置（无 {@code cookie}），鉴权拦截器会据此判定为未绑定并拒绝请求。
     *
     * @return 默认配置
     */
    private AliyuUserConfigBO defaultConfig() {
        return new AliyuUserConfigBO();
    }

    /**
     * 绑定前校验凭证束是否有效，避免把无效凭证落库。
     *
     * @param req 绑定请求
     */
    private void verifyCredential(AliyuUserSettingReqDTO req) {
        if (StrUtil.isEmpty(req.getCookie())) {
            throw new EruptWebApiRuntimeException(AliyuApiErrorCodeEnum.CREDENTIAL_MISSING.getDescription());
        }
        AliyuClientResult<LoginUserRespDTO> result = credentialClient.loginUser(
                req.getCookie(),
                req.getOperateId(),
                req.getSessValue(),
                Objects.requireNonNullElse(req.getSite(), AliyuApiConstants.Site.MP_WEIXIN),
                AliyuApiConstants.Tenant.SR_VALUE);
        if (Objects.isNull(result) || Objects.isNull(result.getData())) {
            throw new EruptWebApiRuntimeException(AliyuApiErrorCodeEnum.CREDENTIAL_INVALID.getDescription());
        }
    }
}
