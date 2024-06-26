package org.dromara.aimer.repository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author bixiu
 */
@Component
@ComponentScan
@MapperScan(basePackages = {"org.dromara.aimer.repository.mapper"})
public class AimerRepositoryContext {
}
