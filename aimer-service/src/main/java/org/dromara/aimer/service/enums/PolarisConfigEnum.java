package org.dromara.aimer.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PolarisConfigEnum {

    MYBATIS_PLUS_GENERATOR_CONFIG("mybatis-plus-generator-config.json", "mybatis-plus代码生成器配置"),
    XXL_JOB_CONFIG("xxl-job-config.json", "xxl-job配置"),
    ;

    private final String polarisKey;

    private final String desc;
}
