package cn.nicomoe.aimer.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.dtflys.forest.http.ForestCookie;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cookie 头工具：解析、序列化，以及合并响应 {@code Set-Cookie}。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CookieUtils {

    /**
     * 解析 {@code Cookie} 头为有序键值对，值中的 {@code =} 原样保留。
     */
    public static Map<String, String> parse(String cookie) {
        Map<String, String> cookieMap = new HashMap<>();
        if (StrUtil.isEmpty(cookie)) {
            return cookieMap;
        }
        for (String singleCookie : StrUtil.splitTrim(cookie, ';')) {
            String[] singleCookieEntry = StrUtil.splitToArray(singleCookie, '=');
            if (CollUtil.size(singleCookieEntry) < 2) {
                continue;
            }
            cookieMap.put(singleCookieEntry[0], singleCookieEntry[1]);
        }
        return cookieMap;
    }

    /**
     * 将 Cookie 键值对序列化为 {@code name=value; name2=value2}。
     */
    public static String format(Map<String, String> cookieMap) {
        if (MapUtil.isEmpty(cookieMap)) {
            return StrUtil.EMPTY;
        }
        return MapUtil.join(cookieMap, "; ", "=");
    }

    /**
     * 合并响应 {@code Set-Cookie}：未过期的覆盖原值，已过期的从 Cookie 中删除。
     */
    public static String merge(String cookie, List<ForestCookie> responseCookies) {
        Map<String, String> cookieMap = parse(cookie);
        if (CollUtil.isEmpty(responseCookies)) {
            return format(cookieMap);
        }
        for (ForestCookie responseCookie : responseCookies) {
            cookieMap.put(responseCookie.getName(), responseCookie.getValue());
        }
        return format(cookieMap);
    }
}
