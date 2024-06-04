package org.dromara.aimer.service.config;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import org.dromara.aimer.service.bo.config.DataSourceConfigBo;
import org.dromara.aimer.service.bo.config.GlobalConfigBo;
import org.dromara.aimer.service.bo.config.PackageConfigBo;
import org.dromara.aimer.service.bo.config.StrategyConfigBo;
import org.dromara.aimer.service.enums.PolarisConfigEnum;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class MybatisPlusGeneratorConfig extends AbstractPolarisConfig {

    @NotNull
    private DataSourceConfigBo dataSourceConfig;

    @NotNull
    private GlobalConfigBo globalConfig;

    @NotNull
    private PackageConfigBo packageConfig;

    @NotNull
    private StrategyConfigBo strategyConfig;

    public DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig.Builder(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword()).build();
    }

    public GlobalConfig getGlobalConfig() {
        return new GlobalConfig.Builder().build();
    }

    public PackageConfig getPackageConfig() {
        return new PackageConfig.Builder().build();
    }

    public StrategyConfig getStrategyConfig() {
        return new StrategyConfig.Builder().build();
    }

    @Override
    String fileName() {
        return PolarisConfigEnum.MYBATIS_PLUS_GENERATOR_CONFIG.getPolarisKey();
    }
}
