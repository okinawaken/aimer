package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuAuthInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuEncryptInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuResponseInterceptor;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import com.dtflys.forest.annotation.BaseRequest;

/**
 * 阿里鱼小程序接口的公共请求配置。
 * <p>
 * 拦截器顺序：字段加密 → 鉴权注入 → 响应处理。加密在鉴权前执行，保证加密只作用于请求体，
 * 且失败时无需额外查询用户配置。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json", interceptor = {AliyuEncryptInterceptor.class, AliyuAuthInterceptor.class, AliyuResponseInterceptor.class})
public interface AliyuBaseClient {
}
