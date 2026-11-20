package cn.nicomoe.aimer.aliyu.service.user.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.constant.AliyuCacheConstants;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserSettingConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserSettingReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserInfoRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuUserService;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.nicomoe.aimer.util.RedisCacheUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 阿里鱼用户设置服务实现，短时会话标识存放于 Redis，按系统用户 ID 隔离。
 */
@Service
@RequiredArgsConstructor
public class AliyuUserSettingServiceImpl implements AliyuUserSettingService {

    private final AliyuUserService userService;

    @Override
    public AliyuUserConfigDTO get(Long userId) {
        AliyuUserSettingConfigBO configBO = new AliyuUserSettingConfigBO();
        configBO.setSession(RedisCacheUtils.Strings.get(AliyuCacheConstants.Credential.sessionKey(userId)));

        AliyuUserConfigDTO configDTO = new AliyuUserConfigDTO();
        configDTO.setUserId(userId);
        configDTO.setConfig(configBO);
        return configDTO;
    }

    @Override
    public void update(Long userId, AliyuUserSettingReqDTO req) {
        String userToken = this.check(req.getSession());

        RedisCacheUtils.Strings.set(AliyuCacheConstants.Credential.sessionKey(userId), req.getSession());
        RedisCacheUtils.Strings.set(AliyuCacheConstants.Credential.operateIdKey(userId), StrUtil.removeAll(Base64.encode(userToken), "="));
        RedisCacheUtils.Keys.delete(AliyuCacheConstants.Credential.cookieKey(userId));
        RedisCacheUtils.Keys.delete(AliyuCacheConstants.Credential.csrfTokenKey(userId));
    }

    @Override
    public void unbind(Long userId) {
        RedisCacheUtils.Keys.delete(AliyuCacheConstants.Credential.sessionKey(userId));
        RedisCacheUtils.Keys.delete(AliyuCacheConstants.Credential.cookieKey(userId));
        RedisCacheUtils.Keys.delete(AliyuCacheConstants.Credential.operateIdKey(userId));
        RedisCacheUtils.Keys.delete(AliyuCacheConstants.Credential.csrfTokenKey(userId));
    }

    @Override
    public String check(String session) {
        UserInfoRespDTO userInfoRespDTO = userService.userInfo(session);
        CheckUtils.throwIfNull(userInfoRespDTO, "阿里鱼小程序凭证无效，请重新输入");
        return userInfoRespDTO.getUserToken();
    }
}
