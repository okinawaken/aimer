package org.dromara.aimer.repository;

import org.dromara.aimer.repository.mapper.BaseMapperPlus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
@MapperScan(basePackageClasses = BaseMapperPlus.class)
public class AimerRepositoryContext {
}
