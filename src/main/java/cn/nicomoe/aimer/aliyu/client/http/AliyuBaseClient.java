package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuResponseInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuRequestInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuValidateResponseInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.provider.AliyuOkHttpClientProvider;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Redirection;
import com.dtflys.forest.backend.okhttp3.OkHttp3;

/**
 * 阿里鱼业务客户端公共配置。
 */
@Redirection(false)
@OkHttp3(client = AliyuOkHttpClientProvider.class)
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json", interceptor = {AliyuRequestInterceptor.class, AliyuResponseInterceptor.class, AliyuValidateResponseInterceptor.class})
public interface AliyuBaseClient {
}
