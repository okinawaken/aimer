package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Header;

/** CSRF token 获取接口，TTL 由响应 {@code expireTime} 决定（实测 60s）。 不继承基础客户端，避免鉴权拦截器获取 CSRF 时递归调用；凭证由调用方传入。 请求头与小程序旧式 helper 保持一致：只带 {@code cookie}、{@code X-MZ-SESSION}、{@code site}， 不带 {@code Mzoperateid}、{@code __sr}、风控头。 */
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json")
public interface AliyuCsrfClient {

    /** 获取 CSRF token。会话标识可为空（未登录场景）。 */
    @Get(url = "/getCsrfToken")
    AliyuClientResult<AliyuCsrfTokenRespDTO> getCsrfToken(
            @Header(value = AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(value = AliyuApiConstants.Header.X_MZ_SESSION) String session,
            @Header(value = AliyuApiConstants.Header.SITE) String site);
}
