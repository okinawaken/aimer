package org.dromara.aimer.console.configuration.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * @author bixiu
 */
@EnableDubbo
@Configuration
@ImportResource(value = {"classpath*:dubbo/dubbo-provider.xml", "classpath*:dubbo/dubbo-consumer.xml"})
public class DubboConfiguration {
}
