package cn.nicomoe.aimer.aliyu.client.http.interceptor.util;

import cn.nicomoe.aimer.aliyu.client.http.AliyuCsrfClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum;
import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/** 运行时凭证缓存，按 Cookie 隔离会话并按写入时间过期。 CSRF 缓存缺失时同步获取，TTL 取响应 {@code expireTime}（缺失时回落到 60s），与会话、操作者标识同源。 */
@Component
@RequiredArgsConstructor
public class AliyuAuthCache {

    /** 服务端未返回有效期时的兜底 TTL，与线上实测值一致。 */
    private static final long CSRF_FALLBACK_TTL_SECONDS = 60L;

    /** 会话有效期响应头缺失时的兜底 TTL。 */
    private static final long SESSION_FALLBACK_TTL_SECONDS = 3_600L;

    /** {@code Mzoperateid} 缓存时长，对齐小程序 {@code age: 43200}（秒）。 */
    private static final Duration OPERATE_ID_TTL = Duration.ofHours(12L);

    private final AliyuCsrfClient csrfClient;

    private final Cache<String, TimedValue> sessionCache = Caffeine.newBuilder()
            .maximumSize(1_024L)
            .expireAfter(new Expiry<String, TimedValue>() {
                @Override
                public long expireAfterCreate(String key, TimedValue value, long currentTime) {
                    return value.ttlNanos();
                }

                @Override
                public long expireAfterUpdate(String key, TimedValue value, long currentTime,
                                              long currentDuration) {
                    return value.ttlNanos();
                }

                @Override
                public long expireAfterRead(String key, TimedValue value, long currentTime,
                                            long currentDuration) {
                    return currentDuration;
                }
            })
            .build();

    private final Cache<String, String> operateIdCache = Caffeine.newBuilder()
            .maximumSize(1_024L)
            .expireAfterWrite(OPERATE_ID_TTL)
            .build();

    private final Cache<String, CsrfToken> csrfCache = Caffeine.newBuilder()
            .maximumSize(1_024L)
            .expireAfter(new Expiry<String, CsrfToken>() {
                @Override
                public long expireAfterCreate(String key, CsrfToken value, long currentTime) {
                    return value.ttlNanos();
                }

                @Override
                public long expireAfterUpdate(String key, CsrfToken value, long currentTime,
                                              long currentDuration) {
                    return currentDuration;
                }

                @Override
                public long expireAfterRead(String key, CsrfToken value, long currentTime,
                                            long currentDuration) {
                    return currentDuration;
                }
            })
            .build();

    public String getSession(AliyuUserConfigBO config) {
        TimedValue value = this.sessionCache.getIfPresent(config.getCookie());
        return Objects.isNull(value) ? null : value.value();
    }

    /** 缓存响应头下发的会话标识，TTL 取 {@code x-mz-session-expire}（秒），缺失时按 1 小时兜底。 */
    public void updateSession(AliyuUserConfigBO config, String session, Long expireSeconds) {
        this.sessionCache.put(config.getCookie(),
                new TimedValue(session, ttlNanos(expireSeconds, SESSION_FALLBACK_TTL_SECONDS)));
    }

    public String getOperateId(AliyuUserConfigBO config) {
        return this.operateIdCache.getIfPresent(config.getCookie());
    }

    public void updateOperateId(AliyuUserConfigBO config, String operateId) {
        this.operateIdCache.put(config.getCookie(), operateId);
    }

    /** 获取 CSRF token，缓存缺失或已过期时同步换取。 */
    public String getCsrf(AliyuUserConfigBO config) {
        return this.csrfCache.get(config.getCookie(), key -> this.fetchCsrf(config)).token();
    }

    private CsrfToken fetchCsrf(AliyuUserConfigBO config) {
        String session = StrUtil.blankToDefault(config.getSessValue(), this.getSession(config));
        AliyuClientResult<AliyuCsrfTokenRespDTO> clientResult =
                csrfClient.getCsrfToken(config.getCookie(), session, AliyuSiteEnum.MP_WEIXIN.getValue());
        CheckUtils.throwIf(!Objects.equals(clientResult.getCode(), AliyuApiConstants.ApiCode.SUCCESS_CODE),
                clientResult.getMsg());
        AliyuCsrfTokenRespDTO data = clientResult.getData();
        CheckUtils.throwIf(Objects.isNull(data) || StrUtil.isBlank(data.getCsrfToken()),
                AliyuApiErrorCodeEnum.CSRF_TOKEN_EMPTY.getDescription());
        return new CsrfToken(data.getCsrfToken(), ttlNanos(data.getExpireTime(), CSRF_FALLBACK_TTL_SECONDS));
    }

    private static long ttlNanos(Long expireSeconds, long fallbackSeconds) {
        if (Objects.isNull(expireSeconds) || expireSeconds <= 0L) {
            return TimeUnit.SECONDS.toNanos(fallbackSeconds);
        }
        return TimeUnit.SECONDS.toNanos(expireSeconds);
    }

    /** CSRF token 及其有效期。 */
    private record CsrfToken(String token, long ttlNanos) {
    }

    /** 带有效期的缓存值（纳秒）。 */
    private record TimedValue(String value, long ttlNanos) {
    }
}
