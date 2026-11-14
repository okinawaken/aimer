package cn.nicomoe.aimer.aliyu.client.http.interceptor.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import cn.nicomoe.aimer.util.CheckUtils;
import com.dtflys.forest.http.ForestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.erupt.upms.service.EruptUserService;

import java.util.Objects;

/** 解析请求中的显式凭证，或按用户 ID 查询配置。 */
@Component
@RequiredArgsConstructor
public class AliyuUserConfigResolver {

    private final EruptUserService userService;
    private final AliyuUserSettingService settingService;

    /** 解析用户配置并校验 Cookie。 */
    public AliyuUserConfigBO resolveConfig(ForestRequest<?> request) {
        AliyuUserConfigBO explicitConfig = this.explicitConfig(request);
        if (Objects.nonNull(explicitConfig)) {
            return explicitConfig;
        }

        Long userId = this.resolveUserId(request);
        CheckUtils.throwIfNull(userId, AliyuApiErrorCodeEnum.NOT_LOGIN.getDescription());

        AliyuUserConfigDTO configDTO = this.settingService.get(userId);
        AliyuUserConfigBO config = Objects.isNull(configDTO) ? null : configDTO.getConfig();
        CheckUtils.throwIf(Objects.isNull(config) || StrUtil.isBlank(config.getCookie()),
                AliyuApiErrorCodeEnum.CREDENTIAL_MISSING.getDescription());
        return config;
    }

    /** 显式凭证中的 Cookie 与会话标识必须同时提供；均未提供时读取已存配置。 */
    private AliyuUserConfigBO explicitConfig(ForestRequest<?> request) {
        String cookie = Convert.toStr(request.getVariableValue(AliyuApiConstants.Variable.COOKIE));
        String sessValue = Convert.toStr(request.getVariableValue(AliyuApiConstants.Variable.SESS_VALUE));
        if (StrUtil.isBlank(cookie) && StrUtil.isBlank(sessValue)) {
            return null;
        }
        CheckUtils.throwIf(StrUtil.isBlank(cookie), AliyuApiErrorCodeEnum.CREDENTIAL_MISSING.getDescription());
        CheckUtils.throwIf(StrUtil.isBlank(sessValue), AliyuApiErrorCodeEnum.SESSION_MISSING.getDescription());

        AliyuUserConfigBO config = new AliyuUserConfigBO();
        config.setCookie(cookie);
        config.setSessValue(sessValue);
        return config;
    }

    private Long resolveUserId(ForestRequest<?> request) {
        Long requestUserId = Convert.toLong(request.getVariableValue(AliyuApiConstants.Variable.USER_ID));
        if (Objects.nonNull(requestUserId)) {
            return requestUserId;
        }
        return this.userService.getCurrentUid();
    }
}
