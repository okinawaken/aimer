package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuEncryptInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuLangTypeInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuRiskInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuTenantInterceptor;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuWeChatLoginReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.LoginAuthLoginRespDTO;
import com.dtflys.forest.annotation.AutoCookieSaveAndLoad;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.FormBody;
import com.dtflys.forest.annotation.Header;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.LogEnabled;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.http.ForestResponse;

import java.util.Map;

/** 登录前的账号操作，不依赖已绑定的阿里鱼账号。 <p> 与业务客户端不同，这里不读取用户配置、不自动换取 CSRF，也不回写响应头里的会话凭证； 调用方按登录流程传入当前 Cookie、会话头与 CSRF 等请求头，并从 {@link ForestResponse} 读取转换值和响应头。 未传的请求头参数不会写入请求。微信授权数据（{@code jsCode}、{@code encryptedData}、{@code iv}） 由小程序客户端授权回调产出，无法离线生成。 请求不重试、不打印日志、不持久化 Cookie，避免账号口令和一次性登录凭证落盘。 <p> 风控头与 {@code langType} 仍需注入：小程序所有登录请求都走同一网络层，同样会带 {@code epeius} / {@code hecate} / {@code coeus} 并追加 {@code langType}。 */
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json",
        headers = AliyuApiConstants.Header.SITE + ": " + AliyuSiteEnum.Code.MP_WEIXIN,
        interceptor = {AliyuTenantInterceptor.class, AliyuRiskInterceptor.class,
                AliyuEncryptInterceptor.class, AliyuLangTypeInterceptor.class}, retryCount = 0)
@AutoCookieSaveAndLoad(value = false)
@LogEnabled(value = false)
public interface AliyuAccountClient {

    /** 微信旧版授权登录，登录路径由渠道动态选择。 参数 body：微信授权载荷，{@code langType} 默认 1 参数 cookie：当前流程的 Cookie 参数 session：maitix 会话标识（{@code X-MZ-SESSION} 头） 参数 csrf：CSRF 令牌（{@code X-XSRF-TOKEN} 头） 返回：登录结果及原始响应头；会话凭证需由调用方自行保存 */
    @Post(url = "/login/authLogin", logEnabled = false)
    ForestResponse<AliyuClientResult<LoginAuthLoginRespDTO>> authLogin(
            @JSONBody AliyuWeChatLoginReqDTO body,
            @Header(value = AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(value = AliyuApiConstants.Header.X_MZ_SESSION) String session,
            @Header(value = AliyuApiConstants.Header.X_XSRF_TOKEN) String csrf);

    /** 账号密码登录，{@code loginPass} / {@code phone} / {@code email} 由拦截器 RSA 加密。 参数 body：账号、密码等登录字段参数 cookie：当前流程的 Cookie 参数 session：maitix 会话标识参数 csrf：CSRF 令牌返回：登录结果，通常含 {@code authToken} / {@code userToken} */
    @Post(url = "/user/userLogin", logEnabled = false)
    ForestResponse<AliyuClientResult<Map<String, Object>>> userLogin(
            @JSONBody Map<String, Object> body,
            @Header(value = AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(value = AliyuApiConstants.Header.X_MZ_SESSION) String session,
            @Header(value = AliyuApiConstants.Header.X_XSRF_TOKEN) String csrf);

    /** 用户校验 V2，{@code userEmail} / {@code loginPass} / {@code userTel} / {@code contactInfo} 由拦截器 RSA 加密。 参数 body：待校验的账号字段参数 cookie：当前流程的 Cookie 参数 session：maitix 会话标识参数 csrf：CSRF 令牌返回：校验结果 */
    @Post(url = "/user/validateUserV2", logEnabled = false)
    ForestResponse<AliyuClientResult<Object>> validateUserV2(
            @JSONBody Map<String, Object> body,
            @Header(value = AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(value = AliyuApiConstants.Header.X_MZ_SESSION) String session,
            @Header(value = AliyuApiConstants.Header.X_XSRF_TOKEN) String csrf);

    /** 注册并设置密码。{@code authCode} 取自 {@code wx.login()}，密码与联系方式由拦截器 RSA 加密。 参数 body：注册字段，含 {@code authCode}、{@code appType}、密码与手机号等参数 cookie：当前流程的 Cookie 参数 session：maitix 会话标识参数 csrf：CSRF 令牌返回：注册结果，含 {@code userToken}、{@code authToken} 及其有效期 */
    @Post(url = "/user/registerSetPass", logEnabled = false)
    ForestResponse<AliyuClientResult<Map<String, Object>>> registerSetPass(
            @JSONBody Map<String, Object> body,
            @Header(value = AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(value = AliyuApiConstants.Header.X_MZ_SESSION) String session,
            @Header(value = AliyuApiConstants.Header.X_XSRF_TOKEN) String csrf);

    /** 找回密码，发送 {@code mobile} / {@code email} 验证码。 小程序使用表单请求体，验证字段与一次性凭证均由调用方按流程提供；{@code mobile} / {@code email} 由拦截器 RSA 加密。 参数 body：表单字段，含手机号或邮箱参数 cookie：当前流程的 Cookie 参数 session：maitix 会话标识参数 csrf：CSRF 令牌返回：验证码发送结果 */
    @Post(url = "/user/findPass", contentType = "application/x-www-form-urlencoded", logEnabled = false)
    ForestResponse<AliyuClientResult<Object>> findPass(
            @FormBody Map<String, Object> body,
            @Header(value = AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(value = AliyuApiConstants.Header.X_MZ_SESSION) String session,
            @Header(value = AliyuApiConstants.Header.X_XSRF_TOKEN) String csrf);

    /** 找回密码校验并设置新密码，{@code newPass} 由拦截器 RSA 加密。 参数 body：表单字段，含验证码与新密码参数 cookie：当前流程的 Cookie 参数 session：maitix 会话标识参数 csrf：CSRF 令牌返回：新密码设置结果 */
    @Post(url = "/user/findPassCheck", contentType = "application/x-www-form-urlencoded", logEnabled = false)
    ForestResponse<AliyuClientResult<Object>> findPassCheck(
            @FormBody Map<String, Object> body,
            @Header(value = AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(value = AliyuApiConstants.Header.X_MZ_SESSION) String session,
            @Header(value = AliyuApiConstants.Header.X_XSRF_TOKEN) String csrf);
}
