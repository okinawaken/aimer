package cn.nicomoe.aimer.aliyu.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliyuCacheConstants {

    /** 会话标识缓存前缀，完整 key：前缀 + sha256(Cookie) */
    public static final String CREDENTIAL_SESSION = "aliyu:credential:session:";

    /** 操作者 ID 缓存前缀，完整 key：前缀 + sha256(Cookie) */
    public static final String CREDENTIAL_OPERATE_ID = "aliyu:credential:operate_id:";

    /** CSRF Token 缓存前缀，完整 key：前缀 + sha256(Cookie) */
    public static final String CREDENTIAL_CSRF_TOKEN = "aliyu:credential:csrf:";

}
