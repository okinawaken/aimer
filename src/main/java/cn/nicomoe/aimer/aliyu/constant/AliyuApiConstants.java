package cn.nicomoe.aimer.aliyu.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

/**
 * 阿里鱼小程序接口常量。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliyuApiConstants {

    /**
     * 网关基础信息
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Gateway {

        /**
         * 业务接口根地址（含 {@code /api} 前缀，urlTemplate 直接拼接其后）
         */
        public static final String BASE_URL = "https://client.maitix.com/api";

        /**
         * 租户地址明文值，由启动接口 {@code /partner/queryMDomain} 返回，发送时须开启 URL 编码。
         */
        public static final String SR_VALUE = "https://h5.alifishofficial.com/";
    }

    /**
     * 会话鉴权请求头。
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class RequestHeader {

        /**
         * 渠道标识头，取值见 {@link cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum}（固定 {@link cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum#MP_WEIXIN}）
         */
        public static final String SITE = "site";

        /**
         * 操作者 ID 头（base64）。服务端响应头回写，43200s 级，运行时缓存复用
         */
        public static final String MZOPERATEID = "Mzoperateid";

        /**
         * maitix 会话头（响应头 {@code x-mz-session} 回写，TTL 360s）
         */
        public static final String X_MZ_SESSION = "X-MZ-SESSION";

        /**
         * 会话有效期响应头（秒），与 {@link #X_MZ_SESSION} 成对下发；小程序按该值决定本地缓存时长
         */
        public static final String X_MZ_SESSION_EXPIRE = "x-mz-session-expire";

        /**
         * CSRF 头（由 {@code GET /getCsrfToken} 取得，TTL 60s，POST 必填）
         */
        public static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";

        /**
         * Cookie 头（JSESSIONID + MZCONSUMERJSESSIONID + MZOPERATEID，持久会话身份）；同时作为 {@code @Var} 传递显式 Cookie 时的变量名
         */
        public static final String COOKIE = "cookie";

        /**
         * 风控头：epeius，由风控拦截器逐次计算。
         */
        public static final String EPEIUS = "epeius";

        /**
         * 风控头：hecate，由风控拦截器逐次计算。
         */
        public static final String HECATE = "hecate";

        /**
         * 风控头：coeus，由风控拦截器逐次计算。
         */
        public static final String COEUS = "coeus";
    }

    /**
     * Cookie 名称
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CookieName {

        /**
         * CSRF 令牌，与请求头 {@link RequestHeader#X_XSRF_TOKEN} 同源
         */
        public static final String CSRF_TOKEN = "XSRF-TOKEN";

        /**
         * 操作者 ID，与请求头 {@link RequestHeader#MZOPERATEID} 同源
         */
        public static final String OPERATE_ID = "MZOPERATEID";
    }

    /**
     * 查询参数名
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Query {

        /**
         * 租户反解析参数：值为 {@link Gateway#SR_VALUE} 的 URL 编码形式，由请求层统一追加到每个业务接口 URL
         */
        public static final String __SR = "__sr";

        /**
         * 语言类型参数：小程序对每个请求 URL 末尾追加，取值见 {@link cn.nicomoe.aimer.aliyu.enums.AliyuLangTypeEnum}， 默认简体 {@code 1}。由 {@code AliyuRequestInterceptor} 统一追加，调用方无需显式传入。
         */
        public static final String LANG_TYPE = "langType";
    }

    /**
     * API 业务响应码
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ApiCode {

        /**
         * 业务成功码（字符串 "200"）。
         */
        public static final String SUCCESS_CODE = "200";

        /**
         * 未登录/凭证失效业务码，小程序按“请登录”处理。
         */
        public static final String NEED_LOGIN_CODE = "603";

        /**
         * 未登录业务码，历史实现中业务接口返回该值时表示凭证失效。
         */
        public static final String NO_LOGIN_CODE = "804";
    }

    /**
     * 小程序端字段级 RSA 加密（PKCS#1 v1.5）配置。
     * <p>小程序请求封装在发送前会按接口路径对指定字段逐个加密，密钥硬编码在小程序包内；
     * 未加密时上游返回“参数错误”。风控头 {@code hecate} 仍按加密前的明文计算。
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Rsa {

        /**
         * 小程序内置 RSA 公钥（X.509 / base64，无 PEM 头尾）。
         */
        public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhmNf45eNxk+ZBPoBdiU0GZazrO2GFyEupFxu5t2LCPNYy++Tn2Cy6KnjVg0Lpr5pYag20dl+qzHrmfw7Z2PleiDKAEZeaCAQQAChR0FgdP0i/Nz2A5+R6le30RqxGJkEGaV3npNT3KzVOXDceboitqRhQy9mM1jEHpKR2qN8/xFAK5otNje7c/h2inB2pWxpXxenSy5El6kwdQVaJcgd6m7fG/6bphNKgE9+OCHrTiUL/QF4YfDWWLOKHOIVNUn0LbZklCrJSeojsbNdX38/uj4gGZsCzLo25i1gpyqESDZ9U5D7DeNNNvoFJ8FlqtynA4eJn8CtW3i+cHihYiqBtwIDAQAB";

        /**
         * 接口路径 → 需要 RSA 加密的字段名。字段可能位于嵌套对象或列表中。
         */
        public static final Map<String, Set<String>> ENCRYPT_FIELDS = Map.ofEntries(
                Map.entry("/msg/sendMobileMsg", Set.of("telephone")),
                Map.entry("/msg/sendEmailMsg", Set.of("email")),
                Map.entry("/user/modifyUser", Set.of("userCard", "emergencyContact", "emergencyContactTelphone")),
                Map.entry("/user/addDis", Set.of("email", "telPhone")),
                Map.entry("/user/modifyDis", Set.of("email", "telPhone")),
                Map.entry("/user/addBearer", Set.of("identification")),
                Map.entry("/trade/submitGroupedCart", Set.of("receiveCard", "receiveName", "paymentPassword")),
                Map.entry("/invoice/simple/applyInvoice", Set.of("buyerTel", "email"))
        );
    }
}
