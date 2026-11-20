package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuResponseInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuRiskInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuTenantInterceptor;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.http.ForestResponse;

/**
 * CSRF token 获取客户端，不继承基础客户端以避免递归获取 CSRF。
 */
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json",
        interceptor = {AliyuResponseInterceptor.class})
public interface AliyuCsrfClient {

    /** 获取 CSRF token 及服务端下发的会话标识。 */
    @Get(url = "/getCsrfToken")
    ForestResponse<AliyuClientResult<AliyuCsrfTokenRespDTO>> getCsrfToken();
}
