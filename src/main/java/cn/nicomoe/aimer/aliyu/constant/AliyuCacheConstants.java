package cn.nicomoe.aimer.aliyu.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 阿里鱼小程序运行态缓存 key 常量，统一按系统用户 ID 隔离。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliyuCacheConstants {

    /**
     * 会话凭证缓存 key。
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Credential {

        public static final String COOKIE = "aliyu:credential:cookie:";

        public static final String SESSION = "aliyu:credential:session:";

        public static final String OPERATE_ID = "aliyu:credential:operate_id:";

        public static final String CSRF_TOKEN = "aliyu:credential:csrf:";

        public static String cookieKey(Long userId) {
            return COOKIE + userId;
        }

        public static String sessionKey(Long userId) {
            return SESSION + userId;
        }

        public static String operateIdKey(Long userId) {
            return OPERATE_ID + userId;
        }

        public static String csrfTokenKey(Long userId) {
            return CSRF_TOKEN + userId;
        }
    }
}
