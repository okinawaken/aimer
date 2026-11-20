package cn.nicomoe.aimer.config.gson.initializing;

import cn.nicomoe.aimer.util.GsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * 把 Erupt 启动时注册的 Gson 消息转换器换成 {@link GsonUtils#getGson()}， 让 HTTP 序列化也支持 {@link java.time.LocalTime}，并与代码级序列化共用同一实例。
 */
@RequiredArgsConstructor
public class GsonConverterSmartInitializingSingleton implements SmartInitializingSingleton {

    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterSingletonsInstantiated() {
        for (HttpMessageConverter<?> converter : requestMappingHandlerAdapter.getMessageConverters()) {
            if (converter instanceof GsonHttpMessageConverter gsonConverter) {
                gsonConverter.setGson(GsonUtils.getGson());
            }
        }
    }
}
