package cn.nicomoe.aimer.config.gson;

import cn.nicomoe.aimer.config.gson.initializing.GsonConverterSmartInitializingSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class GsonConfig {

    /**
     * 注册 Gson 消息转换器定制器。 参数 requestMappingHandlerAdapter：MVC 请求映射处理器适配器返回：转换器定制器
     */
    @Bean
    public GsonConverterSmartInitializingSingleton gsonConverterCustomizer(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        return new GsonConverterSmartInitializingSingleton(requestMappingHandlerAdapter);
    }
}
