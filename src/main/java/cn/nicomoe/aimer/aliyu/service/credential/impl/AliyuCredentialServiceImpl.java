package cn.nicomoe.aimer.aliyu.service.credential.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.nicomoe.aimer.aliyu.client.http.AliyuCsrfClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.constant.AliyuCacheConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuCredentialBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import cn.nicomoe.aimer.aliyu.service.credential.AliyuCredentialService;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.nicomoe.aimer.util.RedisCacheUtils;
import com.dtflys.forest.http.ForestCookie;
import com.dtflys.forest.http.ForestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** 阿里鱼运行态凭证服务实现，短时凭证全部落在 Redis，按 Cookie 摘要隔离。 */
@Service
@RequiredArgsConstructor
public class AliyuCredentialServiceImpl implements AliyuCredentialService {

    /** CSRF 兜底有效期（接口未下发有效期时使用）。 */
    private static final long CSRF_FALLBACK_SECONDS = 60L;

    /** 会话标识兜底有效期（响应缺少 {@code x-mz-session-expire} 时使用）。 */
    private static final long SESSION_FALLBACK_SECONDS = 360L;

    /** 操作者 ID 有效期（单位：秒），对齐小程序的 {@code age: 43200}。 */
    private static final long OPERATE_ID_TTL_SECONDS = 43_200L;

    /** Cookie 属性名（大小写不敏感），从浏览器/抓包复制时可能混在 Cookie 串里，需要丢弃。 */
    private static final Set<String> COOKIE_ATTRIBUTE_NAMES = Set.of(
            "path", "domain", "expires", "max-age", "httponly", "secure", "samesite",
            "priority", "partitioned", "comment", "version");

    /** 粘贴 Cookie 时可能带上的请求头前缀。 */
    private static final String COOKIE_HEADER_PREFIX = "cookie:";

    private final AliyuCsrfClient csrfClient;

    @Override
    public AliyuCredentialBO resolveForRequest(String cookie) {
        String normalized = this.normalize(cookie);
        CheckUtils.throwIf(StrUtil.isBlank(normalized),
                AliyuApiErrorCodeEnum.CREDENTIAL_MISSING.getDescription());

        String session = this.getSession(normalized);
        String operateId = this.getOperateId(normalized);
        String csrf = this.getCsrf(normalized);
        String effectiveCookie = normalized;

        if (StrUtil.isBlank(csrf)) {
            ForestResponse<AliyuClientResult<AliyuCsrfTokenRespDTO>> response = this.fetchCsrf();
            effectiveCookie = this.mergeAndMigrate(normalized, response);
            csrf = this.extractCsrf(normalized, effectiveCookie, response);
            this.saveHeaders(effectiveCookie, response);
            session = StrUtil.blankToDefault(this.getSession(effectiveCookie), session);
            operateId = StrUtil.blankToDefault(this.getOperateId(effectiveCookie), operateId);
        }
        return new AliyuCredentialBO(effectiveCookie, session, csrf, operateId);
    }

    @Override
    public void saveFromResponse(String cookie, ForestResponse<?> response) {
        String normalized = this.normalize(cookie);
        if (StrUtil.isBlank(normalized)) {
            return;
        }
        String merged = this.mergeAndMigrate(normalized, response);
        this.saveHeaders(merged, response);
    }

    @Override
    public void evictCache(String cookie) {
        String normalized = this.normalize(cookie);
        if (StrUtil.isBlank(normalized)) {
            return;
        }
        String digest = this.digest(normalized);
        RedisCacheUtils.Keys.delete(AliyuCacheConstants.CREDENTIAL_SESSION + digest);
        RedisCacheUtils.Keys.delete(AliyuCacheConstants.CREDENTIAL_OPERATE_ID + digest);
        RedisCacheUtils.Keys.delete(AliyuCacheConstants.CREDENTIAL_CSRF_TOKEN + digest);
    }

    private ForestResponse<AliyuClientResult<AliyuCsrfTokenRespDTO>> fetchCsrf() {
        try {
            return csrfClient.getCsrfToken();
        } catch (RuntimeException e) {
            throw this.csrfError(e);
        }
    }

    private EruptWebApiRuntimeException csrfError(RuntimeException e) {
        return new EruptWebApiRuntimeException(
                AliyuApiErrorCodeEnum.CSRF_UNAVAILABLE.getDescription() + "（" + e.getMessage() + "）");
    }

    private String extractCsrf(String originalCookie, String effectiveCookie,
                               ForestResponse<AliyuClientResult<AliyuCsrfTokenRespDTO>> response) {
        AliyuClientResult<AliyuCsrfTokenRespDTO> result = response.getResult();
        CheckUtils.throwIf(Objects.isNull(result)
                        || !Objects.equals(result.getCode(), AliyuApiConstants.ApiCode.SUCCESS_CODE),
                AliyuApiErrorCodeEnum.CSRF_UNAVAILABLE.getDescription());
        AliyuCsrfTokenRespDTO token = result.getData();
        CheckUtils.throwIf(Objects.isNull(token) || StrUtil.isBlank(token.getCsrfToken()),
                AliyuApiErrorCodeEnum.CSRF_TOKEN_EMPTY.getDescription());

        RedisCacheUtils.Strings.set(AliyuCacheConstants.CREDENTIAL_CSRF_TOKEN + this.digest(effectiveCookie),
                token.getCsrfToken(), this.ttl(token.getExpireTime(), CSRF_FALLBACK_SECONDS));
        if (!Objects.equals(originalCookie, effectiveCookie)) {
            RedisCacheUtils.Keys.delete(AliyuCacheConstants.CREDENTIAL_CSRF_TOKEN + this.digest(originalCookie));
        }
        return token.getCsrfToken();
    }

    private void saveHeaders(String cookie, ForestResponse<?> response) {
        String session = response.getHeaderValue(AliyuApiConstants.Header.X_MZ_SESSION);
        Long expireSeconds = Convert.toLong(
                response.getHeaderValue(AliyuApiConstants.Header.X_MZ_SESSION_EXPIRE), null);
        boolean hasExpire = Objects.nonNull(expireSeconds) && expireSeconds > 0;
        // 有有效期才覆盖；否则仅在本地无会话时兜底写入，避免用无期限的旧值覆盖有效会话
        if (StrUtil.isNotBlank(session)
                && (hasExpire || StrUtil.isBlank(this.getSession(cookie)))) {
            RedisCacheUtils.Strings.set(AliyuCacheConstants.CREDENTIAL_SESSION + this.digest(cookie),
                    session, this.ttl(expireSeconds, SESSION_FALLBACK_SECONDS));
        }
        String operateId = response.getHeaderValue(AliyuApiConstants.Header.MZOPERATEID);
        if (StrUtil.isNotBlank(operateId)) {
            RedisCacheUtils.Strings.set(AliyuCacheConstants.CREDENTIAL_OPERATE_ID + this.digest(cookie),
                    operateId, Duration.ofSeconds(OPERATE_ID_TTL_SECONDS));
        }
    }

    private String mergeAndMigrate(String cookie, ForestResponse<?> response) {
        String merged = this.merge(cookie, response.getCookies());
        if (Objects.equals(merged, cookie)) {
            return merged;
        }
        // 服务端把 Cookie 全部删空时拒绝覆盖，避免误删用户凭证
        if (StrUtil.isBlank(merged)) {
            return cookie;
        }
        this.migrate(cookie, merged);
        return merged;
    }

    /** 规范化 Cookie：去 {@code Cookie:} 前缀、换行与属性项，只保留 {@code name=value}。 */
    private String normalize(String rawCookie) {
        if (StrUtil.isBlank(rawCookie)) {
            return StrUtil.EMPTY;
        }
        String cookie = StrUtil.trim(rawCookie);
        if (StrUtil.startWithIgnoreCase(cookie, COOKIE_HEADER_PREFIX)) {
            cookie = StrUtil.trim(StrUtil.subSuf(cookie, COOKIE_HEADER_PREFIX.length()));
        }
        cookie = StrUtil.replace(cookie, "\r", StrUtil.EMPTY);
        cookie = StrUtil.replace(cookie, "\n", StrUtil.EMPTY);
        return this.serialize(this.parse(cookie));
    }

    /** 把响应 {@code Set-Cookie} 合并进请求 Cookie：过期或 Max-Age 非正的删除，其余覆盖。 */
    private String merge(String cookie, List<ForestCookie> cookies) {
        Map<String, String> pairs = this.parse(this.normalize(cookie));
        if (CollUtil.isNotEmpty(cookies)) {
            Date now = new Date();
            for (ForestCookie item : cookies) {
                if (Objects.isNull(item) || StrUtil.isBlank(item.getName())) {
                    continue;
                }
                if (Objects.isNull(item.getValue())) {
                    continue;
                }
                if (this.isDeleted(item, now)) {
                    pairs.remove(item.getName());
                } else {
                    pairs.put(item.getName(), item.getValue());
                }
            }
        }
        return this.serialize(pairs);
    }

    /** Cookie 的 SHA-256 摘要，用作运行态缓存 key 后缀。 */
    private String digest(String cookie) {
        return DigestUtil.sha256Hex(StrUtil.blankToDefault(cookie, StrUtil.EMPTY));
    }

    /** 判断响应下发的 Cookie 是否已删除或过期。 */
    private boolean isDeleted(ForestCookie cookie, Date now) {
        Duration maxAge = cookie.getMaxAge();
        if (Objects.nonNull(maxAge) && (maxAge.isNegative() || maxAge.isZero())) {
            return true;
        }
        Date expires = cookie.getExpires();
        return Objects.nonNull(expires) && expires.before(now);
    }

    /** 解析 Cookie 串为有序的 {@code name=value}，丢弃属性项。 */
    private LinkedHashMap<String, String> parse(String cookie) {
        LinkedHashMap<String, String> pairs = new LinkedHashMap<>();
        if (StrUtil.isBlank(cookie)) {
            return pairs;
        }
        for (String segment : StrUtil.split(cookie, ';')) {
            String item = StrUtil.trim(segment);
            int index = StrUtil.indexOf(item, '=');
            if (index <= 0) {
                continue;
            }
            String name = StrUtil.trim(StrUtil.subPre(item, index));
            String value = StrUtil.trim(StrUtil.subSuf(item, index + 1));
            if (StrUtil.isBlank(name) || StrUtil.startWith(name, "$")
                    || COOKIE_ATTRIBUTE_NAMES.contains(name.toLowerCase(Locale.ROOT))) {
                continue;
            }
            pairs.put(name, value);
        }
        return pairs;
    }

    private String serialize(Map<String, String> pairs) {
        return CollUtil.join(pairs.entrySet(), "; ", entry -> entry.getKey() + "=" + entry.getValue());
    }

    private void migrate(String oldCookie, String newCookie) {
        String oldDigest = this.digest(oldCookie);
        String newDigest = this.digest(newCookie);
        this.migrateKey(AliyuCacheConstants.CREDENTIAL_SESSION, oldDigest, newDigest);
        this.migrateKey(AliyuCacheConstants.CREDENTIAL_OPERATE_ID, oldDigest, newDigest);
        this.migrateKey(AliyuCacheConstants.CREDENTIAL_CSRF_TOKEN, oldDigest, newDigest);
    }

    private void migrateKey(String prefix, String oldDigest, String newDigest) {
        String oldKey = prefix + oldDigest;
        String newKey = prefix + newDigest;
        Object value = RedisCacheUtils.Strings.get(oldKey);
        if (ObjectUtil.isNull(value)) {
            return;
        }
        if (ObjectUtil.isNull(RedisCacheUtils.Strings.get(newKey))) {
            long ttlMillis = RedisCacheUtils.Keys.getTimeToLive(oldKey);
            if (ttlMillis > 0L) {
                RedisCacheUtils.Strings.set(newKey, value, Duration.ofMillis(ttlMillis));
            }
        }
        RedisCacheUtils.Keys.delete(oldKey);
    }

    private String getSession(String cookie) {
        return RedisCacheUtils.Strings.get(AliyuCacheConstants.CREDENTIAL_SESSION + this.digest(cookie));
    }

    private String getOperateId(String cookie) {
        return RedisCacheUtils.Strings.get(AliyuCacheConstants.CREDENTIAL_OPERATE_ID + this.digest(cookie));
    }

    private String getCsrf(String cookie) {
        return RedisCacheUtils.Strings.get(AliyuCacheConstants.CREDENTIAL_CSRF_TOKEN + this.digest(cookie));
    }

    private Duration ttl(Long expireSeconds, long fallbackSeconds) {
        long seconds = Objects.nonNull(expireSeconds) && expireSeconds > 0 ? expireSeconds : fallbackSeconds;
        return Duration.ofSeconds(seconds);
    }
}
