package org.dromara.aimer.service.configuration.translation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.service.configuration.translation.annotation.TranslationType;
import org.dromara.aimer.service.configuration.translation.core.TranslationInterface;
import org.dromara.aimer.service.configuration.translation.handler.TranslationBeanSerializerModifier;
import org.dromara.aimer.service.configuration.translation.handler.TranslationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 翻译模块配置类
 * @author bixiu
 */
@Slf4j
@Configuration
public class TranslationConfig {

    @Autowired
    private List<TranslationInterface<?>> list;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        Map<String, TranslationInterface<?>> map = new HashMap<>(list.size());
        for (TranslationInterface<?> trans : list) {
            if (trans.getClass().isAnnotationPresent(TranslationType.class)) {
                TranslationType annotation = trans.getClass().getAnnotation(TranslationType.class);
                map.put(annotation.type(), trans);
            } else {
                log.warn(trans.getClass().getName() + " 翻译实现类未标注 TranslationType 注解!");
            }
        }
        TranslationHandler.TRANSLATION_MAPPER.putAll(map);
        // 设置 Bean 序列化修改器
        objectMapper.setSerializerFactory(
                objectMapper.getSerializerFactory()
                        .withSerializerModifier(new TranslationBeanSerializerModifier()));
    }

}
