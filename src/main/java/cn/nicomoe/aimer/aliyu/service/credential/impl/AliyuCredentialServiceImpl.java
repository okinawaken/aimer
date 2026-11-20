package cn.nicomoe.aimer.aliyu.service.credential.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.constant.AliyuCacheConstants;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuCredentialBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuCsrfService;
import cn.nicomoe.aimer.aliyu.service.credential.AliyuCredentialService;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.nicomoe.aimer.util.CookieUtils;
import cn.nicomoe.aimer.util.RedisCacheUtils;
import com.dtflys.forest.http.ForestCookie;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.erupt.upms.service.EruptUserService;

import java.util.List;
import java.util.Map;

/**
 * 阿里鱼运行态凭证服务实现，短时凭证全部落在 Redis，按系统用户 ID 隔离。
 * <p>用户只需提供 {@code X-MZ-SESSION}；Cookie 由响应 {@code Set-Cookie} 维护，可缺省。
 */
@Service
@RequiredArgsConstructor
public class AliyuCredentialServiceImpl implements AliyuCredentialService {

    private final AliyuCsrfService csrfService;
    private final EruptUserService userService;

    @Override
    public AliyuCredentialBO resolveForRequest(ForestRequest<?> request) {
        Long userId = userService.getCurrentUid();

        String session = this.resolveSession(request, userId);
        String cookie = this.resolveCookie(request, userId);
        String operateId = this.resolveOperateId(request, userId, cookie);
        String csrf = this.resolveAndSaveCsrf(request, userId, cookie);
        return new AliyuCredentialBO(cookie, session, csrf, operateId);
    }

    @Override
    public void saveFromResponse(ForestRequest<?> request, ForestResponse<?> response) {
        Long userId = userService.getCurrentUid();

        this.saveSession(request, response, userId);
        this.saveCookie(request, response, userId);
        this.saveOperateId(request, response, userId);
    }

    protected String resolveSession(ForestRequest<?> request, Long userId) {
        String session = RedisCacheUtils.Strings.get(AliyuCacheConstants.Credential.sessionKey(userId));
        CheckUtils.throwIfEmpty(session, "未绑定阿里鱼小程序凭证，请先在设置中绑定");
        return session;
    }

    protected String resolveCookie(ForestRequest<?> request, Long userId) {
        return RedisCacheUtils.Strings.get(AliyuCacheConstants.Credential.cookieKey(userId));
    }

    protected String resolveOperateId(ForestRequest<?> request, Long userId, String cookie) {
        return RedisCacheUtils.Strings.get(AliyuCacheConstants.Credential.operateIdKey(userId));
    }

    protected String resolveAndSaveCsrf(ForestRequest<?> request, Long userId, String cookie) {
        Map<String, String> cookieMap = CookieUtils.parse(cookie);
        String cookieCsrf = cookieMap.get(AliyuApiConstants.CookieName.CSRF_TOKEN);
        if (StrUtil.isNotEmpty(cookieCsrf)) {
            RedisCacheUtils.Strings.set(AliyuCacheConstants.Credential.csrfTokenKey(userId), cookieCsrf);
            return cookieCsrf;
        }
        String csrf = RedisCacheUtils.Strings.get(AliyuCacheConstants.Credential.csrfTokenKey(userId));
        if (StrUtil.isNotEmpty(csrf)) {
            return csrf;
        }
        AliyuCsrfTokenRespDTO csrfTokenRespDTO = csrfService.getCsrfToken();
        RedisCacheUtils.Strings.set(AliyuCacheConstants.Credential.csrfTokenKey(userId), csrfTokenRespDTO.getCsrfToken());
        return csrfTokenRespDTO.getCsrfToken();
    }

    protected void saveCookie(ForestRequest<?> request, ForestResponse<?> response, Long userId) {
        List<ForestCookie> responseCookies = response.getCookies();
        if (CollUtil.isNotEmpty(responseCookies)) {
            String cookie = RedisCacheUtils.Strings.get(AliyuCacheConstants.Credential.cookieKey(userId));
            RedisCacheUtils.Strings.set(AliyuCacheConstants.Credential.cookieKey(userId), CookieUtils.merge(cookie, responseCookies));
        }
    }

    protected void saveSession(ForestRequest<?> request, ForestResponse<?> response, Long userId) {
        String session = response.getHeaderValue(AliyuApiConstants.RequestHeader.X_MZ_SESSION);
        if (StrUtil.isNotEmpty(session)) {
            RedisCacheUtils.Strings.set(AliyuCacheConstants.Credential.sessionKey(userId), session);
        }
    }

    protected void saveOperateId(ForestRequest<?> request, ForestResponse<?> response, Long userId) {
        String operateId = response.getHeaderValue(AliyuApiConstants.RequestHeader.MZOPERATEID);
        if (StrUtil.isNotEmpty(operateId)) {
            RedisCacheUtils.Strings.set(AliyuCacheConstants.Credential.operateIdKey(userId), operateId);
        }
    }
}
