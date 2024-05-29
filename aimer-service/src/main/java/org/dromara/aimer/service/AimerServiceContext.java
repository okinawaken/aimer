package org.dromara.aimer.service;

import org.dromara.aimer.repository.AimerRepositoryContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
@Import(AimerRepositoryContext.class)
public class AimerServiceContext {
}
