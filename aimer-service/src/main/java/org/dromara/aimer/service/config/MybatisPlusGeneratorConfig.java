package org.dromara.aimer.service.config;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tencent.polaris.api.rpc.BaseEntity;
import lombok.Data;
import org.dromara.aimer.repository.base.BaseMapperPlus;
import org.dromara.aimer.repository.base.BaseServicePlus;
import org.dromara.aimer.service.bo.config.DataSourceConfigBo;
import org.dromara.aimer.service.bo.config.GlobalConfigBo;
import org.dromara.aimer.service.bo.config.PackageConfigBo;
import org.dromara.aimer.service.bo.config.StrategyConfigBo;
import org.dromara.aimer.service.enums.PolarisConfigEnum;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
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

    @JsonIgnore
    public DataSourceConfig getTargetDataSourceConfig() {
        if (Objects.isNull(dataSourceConfig)) {
            return null;
        }
        return new DataSourceConfig.Builder(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword())
                .build();
    }

    @JsonIgnore
    public GlobalConfig getTargetGlobalConfig() {
        if (Objects.isNull(globalConfig)) {
            return null;
        }
        return new GlobalConfig.Builder()
                .disableOpenDir()
                .outputDir(globalConfig.getOutputDir())
                .author(globalConfig.getAuthor())
                .build();
    }

    @JsonIgnore
    public PackageConfig getTargetPackageConfig() {
        if (Objects.isNull(packageConfig)) {
            return null;
        }
        return new PackageConfig.Builder()
                .parent(packageConfig.getParent())
                .moduleName(packageConfig.getModuleName())
                .entity(packageConfig.getEntity())
                .service(packageConfig.getService())
                .serviceImpl(packageConfig.getServiceImpl())
                .mapper(packageConfig.getMapper())
                .xml(packageConfig.getXml())
                .controller(packageConfig.getController())
                .pathInfo(packageConfig.getPathInfo())
                .build();
    }

    @JsonIgnore
    public StrategyConfig getTargetStrategyConfig() {
        if (Objects.isNull(strategyConfig)) {
            return null;
        }
        return new StrategyConfig.Builder()
                .addInclude(strategyConfig.getIncludes())
                .entityBuilder().superClass(BaseEntity.class).enableFileOverride().formatFileName("%sEntity").enableTableFieldAnnotation().enableLombok().enableChainModel().build()
                .controllerBuilder().enableFileOverride().enableRestStyle().enableHyphenStyle().build()
                .mapperBuilder().superClass(BaseMapperPlus.class).enableFileOverride().build()
                .serviceBuilder().superServiceClass(BaseServicePlus.class).enableFileOverride().build();
    }

    @Override
    String fileName() {
        return PolarisConfigEnum.MYBATIS_PLUS_GENERATOR_CONFIG.getPolarisKey();
    }
}
