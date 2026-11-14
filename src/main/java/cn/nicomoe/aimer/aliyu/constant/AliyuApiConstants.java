package cn.nicomoe.aimer.aliyu.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 阿里鱼小程序（卖座 maitix SaaS 票务 + 阿里鱼娱乐营销叠加层）接口常量。
 * <p>
 * 鉴权为会话凭证束（2026-11-15 真机抓包逆向确认）：{@code __sr} 查询参数做租户反解析，
 * {@code cookie}/{@code Mzoperateid}/{@code X-MZ-SESSION}/{@code X-XSRF-TOKEN}/{@code site} 头做会话鉴权，
 * {@code bx-umidtoken} 等 baxia 头做阿里风控；业务接口无 sign 签名。登录鉴权走 mpassport 加密（RC4+RSA+HMAC）。
 *
 * @author bixiu
 * @since 4.3.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliyuApiConstants {

    /**
     * 网关基础信息
     */
    public static final class Gateway {

        /**
         * 业务接口根地址（含 {@code /api} 前缀，urlTemplate 直接拼接其后）
         */
        public static final String BASE_URL = "https://client.maitix.com/api";

        /**
         * 小程序 appid（wx88adba08e6cf3e75）
         */
        public static final String APPID = "wx88adba08e6cf3e75";
    }

    /**
     * 租户信息（由启动接口 {@code /partner/queryMDomain?miniProgramAppId=} 返回）
     * <p>
     * 阿里鱼 = 阿鲤鱼(杭州)文化传播有限公司，ptnrId {@code 32138}，
     * {@code ptnrDomainMFrontend} = {@code h5.alifishofficial.com}。
     */
    public static final class Tenant {

        /**
         * 商户号（partnerId）
         */
        public static final String PTNR_ID = "32138";

        /**
         * 商户移动端前端域名，用于构造 {@link Query#__SR} 的值
         */
        public static final String PTNR_DOMAIN_MFRONTEND = "h5.alifishofficial.com";

        /**
         * {@code __sr} 参数的明文值：{@code https://<ptnrDomainMFrontend>/}
         * <p>
         * 发送前需做完整 URL 编码（等价小程序 {@code encodeURIComponent}，得到
         * {@code https%3A%2F%2Fh5.alifishofficial.com%2F}），因此经 Forest 发送时必须开启
         * {@code isUrlEncode}（或参数上标注 {@code @URLEncode}），否则 {@code :} 与 {@code /} 会原样透出。
         * </p>
         */
        public static final String SR_VALUE = "https://" + PTNR_DOMAIN_MFRONTEND + "/";
    }

    /**
     * 启动链路接口（appid → 租户引导）
     */
    public static final class Bootstrap {

        /**
         * 取租户 domainInfo：{@code GET /partner/queryMDomain?miniProgramAppId=<appid>}
         */
        public static final String QUERY_M_DOMAIN = "/partner/queryMDomain";

        /**
         * 取 CSRF token（TTL 60s）：{@code GET /getCsrfToken}
         */
        public static final String GET_CSRF_TOKEN = "/getCsrfToken";
    }

    /**
     * 鉴权请求头
     * <p>
     * 真实鉴权为会话凭证束（真机抓包确认），不存在单一 {@code token} 头。
     */
    public static final class Header {

        /**
         * 渠道标识头（固定 {@link Site#MP_WEIXIN}）
         */
        public static final String SITE = "site";

        /**
         * 操作者 ID 头（base64，如 {@code NjE3MDM3MjI} → 61703722）
         */
        public static final String MZOPERATEID = "Mzoperateid";

        /**
         * maitix 会话头（响应头 {@code x-mz-session} 回写，TTL 360s）
         */
        public static final String X_MZ_SESSION = "X-MZ-SESSION";

        /**
         * CSRF 头（由 {@link Bootstrap#GET_CSRF_TOKEN} 取得，TTL 60s，POST 必填）
         */
        public static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";

        /**
         * Cookie 头（JSESSIONID + MZCONSUMERJSESSIONID + MZOPERATEID，持久会话身份）
         */
        public static final String COOKIE = "cookie";
    }

    /**
     * 渠道标识取值
     */
    public static final class Site {

        /**
         * 微信小程序渠道
         */
        public static final String MP_WEIXIN = "mp_weixin";
    }

    /**
     * 查询参数名
     */
    public static final class Query {

        /**
         * 租户反解析参数：值为 {@link Tenant#SR_VALUE} 的 URL 编码形式，
         * 由请求层统一追加到每个业务接口 URL
         */
        public static final String __SR = "__sr";
    }

    /**
     * 阿里风控头
     * <p>
     * 两类性质不同，不要混为一谈：
     * <ul>
     *   <li>{@link #EPEIUS} / {@link #HECATE} / {@link #COEUS}：算法在包内，可离线确定性复算
     *       （时间戳 / MD5(排序后的明文请求体 + epeius) / 路径命中清单决定 10010 或 10086），
     *       当前实现按用户配置注入，未在 Java 侧复算。</li>
     *   <li>{@link #BX_UMIDTOKEN} / {@link #X_TICID}：由阿里安全 SDK 产出，随设备绑定，无法离线生成。</li>
     *   <li>{@link #MINI_JANUS}：解包产物中未出现该头，仅保留可配置字段以便按需注入。</li>
     * </ul>
     */
    public static final class Baxia {

        public static final String EPEIUS = "epeius";

        public static final String BX_UMIDTOKEN = "bx-umidtoken";

        public static final String HECATE = "hecate";

        public static final String X_TICID = "x-ticid";

        public static final String COEUS = "coeus";

        public static final String MINI_JANUS = "mini-janus";
    }

    /**
     * 请求变量名
     */
    public static final class Variable {

        /**
         * 系统用户 ID
         * <p>
         * 由调用方通过 {@code @Var} 传入，用于在无登录上下文（如定时任务线程）中指定凭证归属用户；
         * 未传时取当前登录用户
         */
        public static final String USER_ID = "userId";
    }

    /**
     * API 业务响应码
     */
    public static final class ApiCode {

        /**
         * 业务成功码（字符串 "200"，源码 {@code "200"===S} 判断）
         */
        public static final String SUCCESS_CODE = "200";
    }
}
