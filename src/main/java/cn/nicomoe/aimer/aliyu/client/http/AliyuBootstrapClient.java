package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuTenantInterceptor;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPartnerDomainRespDTO;
import com.dtflys.forest.annotation.AutoCookieSaveAndLoad;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Header;
import com.dtflys.forest.annotation.LogEnabled;
import com.dtflys.forest.annotation.Query;

import java.util.Map;

/** 启动配置查询，不要求已绑定账号。 <p> 两条接口都按小程序启动顺序调用：先取租户域名，再读站点配置。与业务客户端不同， 这里不注入 Cookie、会话头与 CSRF，也不回写响应头中的凭证；需要的请求头由调用方显式传入。 小程序在命中静态缓存时会改写为静态 JSON 地址，这里只保留接口调用形式。 */
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL,
        headers = AliyuApiConstants.Header.SITE + ": " + AliyuSiteEnum.Code.MP_WEIXIN)
@AutoCookieSaveAndLoad(value = false)
@LogEnabled(value = false)
public interface AliyuBootstrapClient {

    /** 按小程序 AppID 反查租户域名与商户信息。 <p> 该接口不追加 {@code __sr}，也不发送 Cookie 与会话头；返回的 {@code ptnrDomainMFrontend} 是后续业务请求生成租户参数的来源，商户 ID 不要与环境自带的调试商户号混用。 参数 appId：小程序 AppID 参数 langType：语言类型（简体 1） 参数 cookie：当前流程的 Cookie，可为空参数 session：maitix 会话标识，可为空返回：租户域名、商户 ID 与名称 */
    @Get(url = "/partner/queryMDomain", logEnabled = false)
    AliyuClientResult<AliyuPartnerDomainRespDTO> queryMDomain(
            @Query(value = "miniProgramAppId") String appId,
            @Query(value = "langType", defaultValue = "1") Integer langType,
            @Header(value = AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(value = AliyuApiConstants.Header.X_MZ_SESSION) String session);

    /** 读取当前租户的站点配置，请求会追加 {@code __sr} 租户参数。 参数 langType：语言类型（简体 1） 参数 cookie：当前流程的 Cookie，可为空参数 session：maitix 会话标识，可为空返回：站点配置原样返回，字段随首页模板变化 */
    @Get(url = "/cms/querySiteConfig", interceptor = AliyuTenantInterceptor.class, logEnabled = false)
    AliyuClientResult<Map<String, Object>> querySiteConfig(
            @Query(value = "langType", defaultValue = "1") Integer langType,
            @Header(value = AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(value = AliyuApiConstants.Header.X_MZ_SESSION) String session);
}
