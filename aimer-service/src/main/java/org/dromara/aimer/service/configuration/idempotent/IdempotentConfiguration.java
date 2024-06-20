package org.dromara.aimer.service.configuration.idempotent;

import org.dromara.aimer.service.configuration.idempotent.aspectj.RepeatSubmitAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 幂等功能配置
 * @author bixiu
 */
@Configuration
public class IdempotentConfiguration {

    @Bean
    public RepeatSubmitAspect repeatSubmitAspect() {
        return new RepeatSubmitAspect();
    }
}
