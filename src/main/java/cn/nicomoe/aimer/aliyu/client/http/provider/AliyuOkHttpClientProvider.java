package cn.nicomoe.aimer.aliyu.client.http.provider;

import cn.hutool.core.collection.CollUtil;
import com.dtflys.forest.backend.okhttp3.conn.OkHttp3ConnectionManager;
import com.dtflys.forest.handler.LifeCycleHandler;
import com.dtflys.forest.http.ForestRequest;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 阿里鱼 OkHttp 客户端提供者。
 */
public class AliyuOkHttpClientProvider extends OkHttp3ConnectionManager.DefaultOkHttpClientProvider {

    private static final String HEADER_SET_COOKIE = "Set-Cookie";

    private static final Pattern EMPTY_DOMAIN = Pattern.compile("(?i);\\s*Domain\\s*=(?=\\s*(;|$))");

    @Override
    public OkHttpClient getClient(ForestRequest request, LifeCycleHandler lifeCycleHandler) {
        OkHttpClient client = super.getClient(request, lifeCycleHandler);
        return client.newBuilder()
                .addInterceptor(chain -> {
                    Response response = chain.proceed(chain.request());
                    List<String> setCookies = response.headers(HEADER_SET_COOKIE);
                    if (CollUtil.isEmpty(setCookies)) {
                        return response;
                    }
                    if (setCookies.stream().noneMatch(cookie -> EMPTY_DOMAIN.matcher(cookie).find())) {
                        return response;
                    }
                    Response.Builder builder = response.newBuilder().removeHeader(HEADER_SET_COOKIE);
                    setCookies.forEach(cookie -> builder.addHeader(HEADER_SET_COOKIE, EMPTY_DOMAIN.matcher(cookie).replaceAll("")));
                    return builder.build();
                })
                .build();
    }
}
