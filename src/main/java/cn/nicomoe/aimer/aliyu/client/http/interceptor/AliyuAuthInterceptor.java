package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.util.AliyuAuthCache;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.util.AliyuRequestUtils;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.util.AliyuUserConfigResolver;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import cn.nicomoe.aimer.util.CheckUtils;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/** 阿里鱼鉴权拦截器，注入会话凭证与租户参数。 显式凭证用于绑定前校验；否则按指定用户 ID 或当前登录用户读取配置。 */
@Component
@RequiredArgsConstructor
public class AliyuAuthInterceptor implements ForestInterceptor {

    private final AliyuUserConfigResolver configResolver;
    private final AliyuAuthCache credentialCache;

    @Override
    public boolean beforeExecute(ForestRequest request) {
        request.addQuery(AliyuApiConstants.Query.__SR, AliyuApiConstants.Gateway.SR_VALUE, true, StandardCharsets.UTF_8.name());
        request.addHeader(AliyuApiConstants.Header.SITE, AliyuSiteEnum.MP_WEIXIN.getValue());

        AliyuUserConfigBO config = this.configResolver.resolveConfig(request);
        request.addAttachment(AliyuRequestUtils.ATTACHMENT_USER_CONFIG, config);

        request.addHeader(AliyuApiConstants.Header.COOKIE, config.getCookie());
        String operateId = this.credentialCache.getOperateId(config);
        if (StrUtil.isNotBlank(operateId)) {
            request.addHeader(AliyuApiConstants.Header.MZOPERATEID, operateId);
        }
        request.addHeader(AliyuApiConstants.Header.X_MZ_SESSION, this.session(config));
        request.addHeader(AliyuApiConstants.Header.X_XSRF_TOKEN, this.credentialCache.getCsrf(config));
        return true;
    }

    /** 会话标识优先使用用户配置，缺失时回落到运行时缓存。 */
    private String session(AliyuUserConfigBO config) {
        String session = StrUtil.blankToDefault(config.getSessValue(), this.credentialCache.getSession(config));
        CheckUtils.throwIf(StrUtil.isBlank(session), AliyuApiErrorCodeEnum.SESSION_MISSING.getDescription());
        return session;
    }
}
