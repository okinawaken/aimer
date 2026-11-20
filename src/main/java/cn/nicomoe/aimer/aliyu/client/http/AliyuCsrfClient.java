package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuValidateResponseInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.provider.AliyuOkHttpClientProvider;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Redirection;
import com.dtflys.forest.backend.okhttp3.OkHttp3;

/**
 * CSRF token 获取客户端
 */
@Redirection(false)
@OkHttp3(client = AliyuOkHttpClientProvider.class)
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json", interceptor = {AliyuValidateResponseInterceptor.class})
public interface AliyuCsrfClient {

    /**
     * 获取 CSRF token 及服务端下发的会话标识。
     */
    @Get(url = "/getCsrfToken")
    AliyuClientResult<AliyuCsrfTokenRespDTO> getCsrfToken();
}
