package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.client.http.AliyuCsrfClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 阿里鱼运行时凭证缓存。
 * <p>
 * 两类短时效凭证需要跨请求复用与自动续期：
 * <ul>
 *   <li>{@code X-XSRF-TOKEN}——过期前自动调 {@code /getCsrfToken} 换取，有效期取响应中的 {@code expireTime}（实测 60s）</li>
 *   <li>{@code X-MZ-SESSION}——由响应拦截器按响应头 {@code x-mz-session} 回写；小程序侧「本地已有值时不覆盖」，
 *       此处以响应头为准更新缓存，下次请求复用</li>
 * </ul>
 * 缓存键为 Cookie 指纹，保证同一系统用户的不同会话互不串用。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AliyuCredentialCache {

    /**
     * CSRF 提前过期余量（毫秒），避免临界时刻发起请求已失效
     */
    private static final long CSRF_SAFE_MARGIN_MILLIS = 5_000L;

    private final AliyuCsrfClient csrfClient;

    /**
     * CSRF 缓存：cookie 指纹 → 值 + 过期时间戳
     */
    private final Map<String, CsrfEntry> csrfCache = new ConcurrentHashMap<>();

    /**
     * 会话缓存：cookie 指纹 → X-MZ-SESSION 值
     */
    private final Map<String, String> sessionCache = new ConcurrentHashMap<>();

    /**
     * 取可用 CSRF token，缓存缺失或临近过期时自动刷新。
     *
     * @param config 用户凭证配置
     * @return CSRF token；获取失败时返回配置中的旧值（可能为空）
     */
    public String csrf(AliyuUserConfigBO config) {
        String key = this.fingerprint(config);
        CsrfEntry entry = this.csrfCache.get(key);
        if (Objects.nonNull(entry) && entry.expireAt > System.currentTimeMillis() + CSRF_SAFE_MARGIN_MILLIS) {
            return entry.value;
        }
        String fetched = this.fetchCsrf(config);
        if (Objects.nonNull(fetched)) {
            return fetched;
        }
        return Objects.nonNull(entry) ? entry.value : config.getCsrfValue();
    }

    /**
     * 调用 {@code /getCsrfToken} 换取新 token 并写入缓存。
     *
     * @param config 用户凭证配置
     * @return 新 CSRF token；失败返回 {@code null}
     */
    public String fetchCsrf(AliyuUserConfigBO config) {
        String cookie = config.getCookie();
        String operateId = config.getOperateId();
        if (Objects.isNull(cookie) || Objects.isNull(operateId)) {
            return null;
        }
        String site = Objects.requireNonNullElse(config.getSite(), AliyuApiConstants.Site.MP_WEIXIN);
        try {
            AliyuClientResult<AliyuCsrfTokenRespDTO> result = this.csrfClient.getCsrfToken(
                    cookie, operateId, site);
            if (Objects.isNull(result) || Objects.isNull(result.getData())) {
                return null;
            }
            AliyuCsrfTokenRespDTO data = result.getData();
            String token = data.getCsrfToken();
            if (Objects.isNull(token) || token.isEmpty()) {
                return null;
            }
            long ttlSeconds = Objects.requireNonNullElse(data.getExpireTime(), 60L);
            this.csrfCache.put(this.fingerprint(config),
                    new CsrfEntry(token, System.currentTimeMillis() + ttlSeconds * 1000L));
            return token;
        } catch (RuntimeException e) {
            // 取 CSRF 失败时不在此处抛出：调用方（鉴权拦截器）会发现取值为空并终止本次请求
            log.warn("阿里鱼 CSRF token 获取失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 读取缓存的 maitix 会话标识。
     *
     * @param config 用户凭证配置
     * @return 会话标识；无缓存时回落到配置值
     */
    public String session(AliyuUserConfigBO config) {
        String cached = this.sessionCache.get(this.fingerprint(config));
        return Objects.nonNull(cached) ? cached : config.getSessValue();
    }

    /**
     * 写入响应头回传的新会话标识。
     *
     * @param config  用户凭证配置
     * @param session 新会话标识
     */
    public void updateSession(AliyuUserConfigBO config, String session) {
        if (Objects.nonNull(session) && !session.isEmpty()) {
            this.sessionCache.put(this.fingerprint(config), session);
        }
    }

    private String fingerprint(AliyuUserConfigBO config) {
        return Objects.requireNonNullElse(config.getCookie(), "");
    }

    private record CsrfEntry(String value, long expireAt) {
    }
}
