package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuRiskInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuCredentialInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuResponseInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuTenantInterceptor;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import com.dtflys.forest.annotation.BaseRequest;

/**
 * 阿里鱼业务客户端公共配置。
 */
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json",
        interceptor = {AliyuRiskInterceptor.class, AliyuTenantInterceptor.class, AliyuCredentialInterceptor.class, AliyuResponseInterceptor.class})
public interface AliyuBaseClient {
}
