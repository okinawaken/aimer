package cn.nicomoe.aimer.aliyu.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** 阿里鱼小程序接口常量。 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliyuApiConstants {

    /** 网关基础信息 */
    public static final class Gateway {

        /** 业务接口根地址（含 {@code /api} 前缀，urlTemplate 直接拼接其后） */
        public static final String BASE_URL = "https://client.maitix.com/api";

        /** 租户地址明文值，由启动接口 {@code /partner/queryMDomain} 返回，发送时须开启 URL 编码。 */
        public static final String SR_VALUE = "https://h5.alifishofficial.com/";
    }

    /** 会话鉴权请求头。 */
    public static final class Header {

        /** 渠道标识头，取值见 {@link cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum}（固定 {@link cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum#MP_WEIXIN}） */
        public static final String SITE = "site";

        /** 操作者 ID 头（base64）。服务端响应头回写，43200s 级，运行时缓存复用 */
        public static final String MZOPERATEID = "Mzoperateid";

        /** maitix 会话头（响应头 {@code x-mz-session} 回写，TTL 360s） */
        public static final String X_MZ_SESSION = "X-MZ-SESSION";

        /** 会话有效期响应头（秒），与 {@link #X_MZ_SESSION} 成对下发；小程序按该值决定本地缓存时长 */
        public static final String X_MZ_SESSION_EXPIRE = "x-mz-session-expire";

        /** CSRF 头（由 {@code GET /getCsrfToken} 取得，TTL 60s，POST 必填） */
        public static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";

        /** Cookie 头（JSESSIONID + MZCONSUMERJSESSIONID + MZOPERATEID，持久会话身份）；同时作为 {@code @Var} 传递显式 Cookie 时的变量名 */
        public static final String COOKIE = "cookie";

        /** 风控头：epeius，由风控拦截器逐次计算。 */
        public static final String EPEIUS = "epeius";

        /** 风控头：hecate，由风控拦截器逐次计算。 */
        public static final String HECATE = "hecate";

        /** 风控头：coeus，由风控拦截器逐次计算。 */
        public static final String COEUS = "coeus";
    }

    /** 查询参数名 */
    public static final class Query {

        /** 租户反解析参数：值为 {@link Gateway#SR_VALUE} 的 URL 编码形式，由请求层统一追加到每个业务接口 URL */
        public static final String __SR = "__sr";

        /** 语言类型参数：小程序对每个请求 URL 末尾追加，取值见 {@link cn.nicomoe.aimer.aliyu.enums.AliyuLangTypeEnum}， 默认简体 {@code 1}。由 {@code AliyuTenantInterceptor} 统一追加，调用方无需显式传入。 */
        public static final String LANG_TYPE = "langType";
    }

    /** 请求变量名 */
    public static final class Variable {

        /** 系统用户 ID。由调用方通过 {@code @Var} 传入，用于在无登录上下文（如定时任务线程） 中指定凭证归属用户；未传时取当前登录用户 */
        public static final String USER_ID = "userId";
    }

    /** API 业务响应码 */
    public static final class ApiCode {

        /** 业务成功码（字符串 "200"）。 */
        public static final String SUCCESS_CODE = "200";

        /** 未登录/凭证失效业务码，小程序按“请登录”处理。 */
        public static final String NEED_LOGIN_CODE = "603";

        /** 未登录业务码，历史实现中业务接口返回该值时表示凭证失效。 */
        public static final String NO_LOGIN_CODE = "804";
    }
}
