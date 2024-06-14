package org.dromara.aimer.service.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.aimer.service.enums.PolarisConfigEnum;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class XxlJobConfig extends AbstractPolarisConfig {

    @NotNull
    private String address;

    @NotNull
    private String accessToken;

    @Override
    String fileName() {
        return PolarisConfigEnum.XXL_JOB_CONFIG.getPolarisKey();
    }
}
