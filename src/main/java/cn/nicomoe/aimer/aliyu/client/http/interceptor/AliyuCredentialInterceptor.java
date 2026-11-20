package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuCredentialBO;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserSettingConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.service.credential.AliyuCredentialService;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.erupt.upms.service.EruptUserService;

import java.util.Objects;

/**
 * 凭证拦截器：请求前注入认证头，响应后回写服务端下发的短时凭证。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AliyuCredentialInterceptor implements ForestInterceptor {

    private final AliyuCredentialService credentialService;
    private final AliyuUserSettingService settingService;
    private final EruptUserService userService;

    @Override
    public boolean beforeExecute(ForestRequest request) {
        String cookie = request.getHeaderValue(AliyuApiConstants.Header.COOKIE);
        if (StrUtil.isBlank(cookie)) {
            Long userId = Convert.toLong(request.getVariableValue(AliyuApiConstants.Variable.USER_ID));
            if (Objects.isNull(userId)) {
                userId = userService.getCurrentUid();
            }
            AliyuUserConfigDTO configDTO = settingService.get(userId);
            AliyuUserSettingConfigBO configBO = configDTO.getConfig();
            cookie = configBO.getCookie();
        }

        AliyuCredentialBO credential = credentialService.resolveForRequest(cookie);
        request.addHeader(AliyuApiConstants.Header.COOKIE, credential.getCookie());
        request.addHeader(AliyuApiConstants.Header.X_MZ_SESSION, credential.getSession());
        request.addHeader(AliyuApiConstants.Header.MZOPERATEID, credential.getOperateId());
        request.addHeader(AliyuApiConstants.Header.X_XSRF_TOKEN, credential.getCsrf());
        return true;
    }

    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
        String effectiveCookie = request.getHeaderValue(AliyuApiConstants.Header.COOKIE);
        if (StrUtil.isBlank(effectiveCookie)) {
            return;
        }
        try {
            credentialService.saveFromResponse(effectiveCookie, response);
        } catch (Exception e) {
            // 回写失败不能掩盖真实业务响应
            log.warn("阿里鱼凭证回写失败", e);
        }
    }
}
