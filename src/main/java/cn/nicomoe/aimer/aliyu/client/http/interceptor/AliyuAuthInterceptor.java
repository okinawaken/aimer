package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.hutool.core.convert.Convert;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestRequestType;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.upms.service.EruptUserService;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 阿里鱼鉴权拦截器。
 * <p>
 * 按真机抓包还原的会话凭证束注入请求：{@code cookie} + {@code Mzoperateid} +
 * {@code X-MZ-SESSION} + {@code X-XSRF-TOKEN} + {@code site} + baxia 头组，
 * 并为每个业务请求追加租户反解析参数 {@code __sr}。
 * </p>
 * <p>
 * <b>全量鉴权</b>：本项目不区分小程序侧的特殊接口白名单，所有业务接口一律要求凭证齐全。
 * 缺少登录上下文、未绑定凭证或 POST 取不到 CSRF 时直接抛出，不发起请求——避免以匿名身份
 * 打到上游并拿到误导性的业务错误。
 * </p>
 * <p>
 * 注意：本拦截器只处理 header / query，不触碰请求体；请求体的字段加密由排在链首的
 * {@code AliyuEncryptInterceptor} 负责。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@Component
@RequiredArgsConstructor
public class AliyuAuthInterceptor implements ForestInterceptor {

    private final EruptUserService userService;
    private final AliyuUserSettingService settingService;
    private final AliyuCredentialCache credentialCache;

    @Override
    public boolean beforeExecute(ForestRequest request) {
        // 租户反解析参数，所有业务接口必带；小程序侧做了 encodeURIComponent，故此处开启完整编码
        if (Objects.isNull(request.getQuery(AliyuApiConstants.Query.__SR))) {
            request.addQuery(AliyuApiConstants.Query.__SR, AliyuApiConstants.Tenant.SR_VALUE, true, StandardCharsets.UTF_8.name());
        }
        Long userId = this.resolveUserId(request);
        AliyuUserConfigBO config = this.resolveConfig(userId);
        this.injectHeaders(request, config);
        return true;
    }

    /**
     * 注入凭证束请求头；任一必需凭证缺失即抛出。
     *
     * @param request Forest 请求
     * @param config  用户凭证配置
     */
    private void injectHeaders(ForestRequest<?> request, AliyuUserConfigBO config) {
        if (Objects.isNull(config.getCookie()) || config.getCookie().isEmpty()) {
            throw new EruptWebApiRuntimeException(AliyuApiErrorCodeEnum.CREDENTIAL_MISSING.getDescription());
        }
        request.addHeader(AliyuApiConstants.Header.COOKIE, config.getCookie());
        request.addHeader(AliyuApiConstants.Header.MZOPERATEID,
                Objects.requireNonNullElse(config.getOperateId(), ""));
        request.addHeader(AliyuApiConstants.Header.SITE,
                Objects.requireNonNullElse(config.getSite(), AliyuApiConstants.Site.MP_WEIXIN));

        String session = this.credentialCache.session(config);
        if (Objects.nonNull(session) && !session.isEmpty()) {
            request.addHeader(AliyuApiConstants.Header.X_MZ_SESSION, session);
        }

        // POST 强制要求 CSRF：取不到即失败，不放行
        if (ForestRequestType.POST.equals(request.getType())) {
            String csrf = this.credentialCache.csrf(config);
            if (Objects.isNull(csrf) || csrf.isEmpty()) {
                throw new EruptWebApiRuntimeException(AliyuApiErrorCodeEnum.CSRF_UNAVAILABLE.getDescription());
            }
            request.addHeader(AliyuApiConstants.Header.X_XSRF_TOKEN, csrf);
        }

        AliyuUserConfigBO.Baxia baxia = config.getBaxia();
        if (Objects.nonNull(baxia)) {
            this.addIfPresent(request, AliyuApiConstants.Baxia.EPEIUS, baxia.getEpeius());
            this.addIfPresent(request, AliyuApiConstants.Baxia.BX_UMIDTOKEN, baxia.getBaxiaUmid());
            this.addIfPresent(request, AliyuApiConstants.Baxia.HECATE, baxia.getHecate());
            this.addIfPresent(request, AliyuApiConstants.Baxia.X_TICID, baxia.getXTicid());
            this.addIfPresent(request, AliyuApiConstants.Baxia.COEUS, baxia.getCoeus());
            this.addIfPresent(request, AliyuApiConstants.Baxia.MINI_JANUS, baxia.getBaxiaJanus());
        }
    }

    private void addIfPresent(ForestRequest<?> request, String name, String value) {
        if (Objects.nonNull(value) && !value.isEmpty()) {
            request.addHeader(name, value);
        }
    }

    /**
     * 解析凭证归属用户：优先取 {@code @Var("userId")}，否则取当前登录用户。
     *
     * @param request Forest 请求
     * @return 用户 ID，必然非空
     */
    private Long resolveUserId(ForestRequest<?> request) {
        Long requestUserId = Convert.toLong(request.getVariableValue(AliyuApiConstants.Variable.USER_ID));
        if (Objects.nonNull(requestUserId)) {
            return requestUserId;
        }
        Long currentUid;
        try {
            currentUid = userService.getCurrentUid();
        } catch (RuntimeException e) {
            throw new EruptWebApiRuntimeException(AliyuApiErrorCodeEnum.NOT_LOGIN.getDescription(), e);
        }
        if (Objects.isNull(currentUid)) {
            throw new EruptWebApiRuntimeException(AliyuApiErrorCodeEnum.NOT_LOGIN.getDescription());
        }
        return currentUid;
    }

    /**
     * 读取用户凭证配置，缺失即抛出。
     *
     * @param userId 用户 ID
     * @return 凭证配置，必然非空
     */
    private AliyuUserConfigBO resolveConfig(Long userId) {
        AliyuUserConfigDTO configDTO = settingService.get(userId);
        AliyuUserConfigBO config = Objects.nonNull(configDTO) ? configDTO.getConfig() : null;
        if (Objects.isNull(config)) {
            throw new EruptWebApiRuntimeException(AliyuApiErrorCodeEnum.CREDENTIAL_MISSING.getDescription());
        }
        return config;
    }
}
