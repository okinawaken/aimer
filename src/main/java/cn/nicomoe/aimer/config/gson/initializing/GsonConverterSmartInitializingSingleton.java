package cn.nicomoe.aimer.config.gson.initializing;

import cn.nicomoe.aimer.config.gson.GsonConfig;
import cn.nicomoe.aimer.util.GsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Gson 消息转换器定制。
 * <p>
 * Erupt 的 {@code MvcConfig} 在启动时用 {@code GsonFactory.getGson()} 注册了一个 Gson 消息转换器，
 * 该实例不含 {@link java.time.LocalTime} 适配器。这里在容器初始化完成后，
 * 将转换器内的 Gson 统一替换为 {@link GsonUtils#getGson()}，使 HTTP 序列化与代码级序列化共用同一个 Gson、支持 {@code LocalTime}。
 * </p>
 * <p>
 * 由 {@link GsonConfig} 以 {@code @Bean} 方式注册。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
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
