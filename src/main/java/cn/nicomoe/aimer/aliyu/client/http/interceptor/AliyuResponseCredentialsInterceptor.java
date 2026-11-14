package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.util.AliyuAuthCache;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.util.AliyuRequestUtils;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.util.AliyuUserConfigResolver;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/** 缓存响应头中的会话凭证。 <p> 小程序在判断业务码之前就保存 {@code Mzoperateid} 与 {@code X-MZ-SESSION}（含 {@code x-mz-session-expire}）， 因此即使响应是未登录等错误码也要回写；本拦截器需排在 {@link AliyuResponseInterceptor} 之前。 */
@Component
@RequiredArgsConstructor
public class AliyuResponseCredentialsInterceptor implements ForestInterceptor {

    private final AliyuAuthCache credentialCache;
    private final AliyuUserConfigResolver configResolver;

    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
        String newSession = response.getHeaderValue(AliyuApiConstants.Header.X_MZ_SESSION);
        String newOperateId = response.getHeaderValue(AliyuApiConstants.Header.MZOPERATEID);
        if (StrUtil.isBlank(newSession) && StrUtil.isBlank(newOperateId)) {
            return;
        }
        AliyuUserConfigBO config = this.config(request);
        if (Objects.isNull(config)) {
            return;
        }
        if (StrUtil.isNotBlank(newSession)) {
            Long expireSeconds = Convert.toLong(
                    response.getHeaderValue(AliyuApiConstants.Header.X_MZ_SESSION_EXPIRE), null);
            this.credentialCache.updateSession(config, newSession, expireSeconds);
        }
        if (StrUtil.isNotBlank(newOperateId)) {
            this.credentialCache.updateOperateId(config, newOperateId);
        }
    }

    /** 优先复用鉴权拦截器已解析的配置，避免重复查库；显式凭证场景下同样可用。 */
    private AliyuUserConfigBO config(ForestRequest<?> request) {
        Object attached = request.getAttachment(AliyuRequestUtils.ATTACHMENT_USER_CONFIG);
        if (attached instanceof AliyuUserConfigBO config) {
            return config;
        }
        return this.configResolver.resolveConfig(request);
    }
}
