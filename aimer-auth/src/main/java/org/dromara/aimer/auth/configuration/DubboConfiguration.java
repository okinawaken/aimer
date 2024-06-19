package org.dromara.aimer.auth.configuration;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@EnableDubbo
@Configuration
@ImportResource(value = {"classpath*:dubbo/dubbo-consumer.xml"})
public class DubboConfiguration {
}
