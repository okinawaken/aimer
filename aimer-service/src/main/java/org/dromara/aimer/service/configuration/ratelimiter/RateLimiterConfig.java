package org.dromara.aimer.service.configuration.ratelimiter;

import org.dromara.aimer.service.configuration.ratelimiter.aspectj.RateLimiterAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RateLimiterConfig {

    @Bean
    public RateLimiterAspect rateLimiterAspect() {
        return new RateLimiterAspect();
    }

}
