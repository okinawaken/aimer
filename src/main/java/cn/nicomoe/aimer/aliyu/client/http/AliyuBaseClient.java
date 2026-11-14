package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuAuthInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuEncryptInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuLangTypeInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuResponseCredentialsInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuResponseInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuRiskInterceptor;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import com.dtflys.forest.annotation.BaseRequest;

/** 阿里鱼接口公共配置。拦截器顺序：风控 → 字段加密 → 语言参数 → 鉴权 → 凭证回写 → 响应校验； 风控必须最先（{@code hecate} 签明文请求体），加密必须在鉴权前， 语言参数在风控之后追加（不参与 {@code hecate} 签名，与小程序 URL 拼接顺序一致）。 凭证回写必须早于响应校验：小程序先保存响应头（会话/操作者），再判断业务码。 */
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json",
        interceptor = {AliyuRiskInterceptor.class, AliyuEncryptInterceptor.class, AliyuLangTypeInterceptor.class, AliyuAuthInterceptor.class, AliyuResponseCredentialsInterceptor.class, AliyuResponseInterceptor.class})
public interface AliyuBaseClient {
}
