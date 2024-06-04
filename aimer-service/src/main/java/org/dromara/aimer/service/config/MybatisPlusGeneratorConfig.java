package org.dromara.aimer.service.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.aimer.service.enums.PolarisConfigEnum;
import org.springframework.stereotype.Component;

@Data
@Component
@EqualsAndHashCode(callSuper = true)
public class MybatisPlusGeneratorConfig extends AbstractPolarisConfig {

    private String author;

    @Override
    String fileName() {
        return PolarisConfigEnum.MYBATIS_PLUS_GENERATOR_CONFIG.getPolarisKey();
    }
}
