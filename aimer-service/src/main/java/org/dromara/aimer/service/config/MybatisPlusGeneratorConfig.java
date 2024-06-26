package org.dromara.aimer.service.config;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;
import org.dromara.aimer.repository.base.BaseDO;
import org.dromara.aimer.repository.base.BaseMapperPlus;
import org.dromara.aimer.repository.base.BaseServicePlus;
import org.dromara.aimer.service.dto.DataSourceConfigDTO;
import org.dromara.aimer.service.dto.GlobalConfigDTO;
import org.dromara.aimer.service.dto.PackageConfigDTO;
import org.dromara.aimer.service.dto.StrategyConfigDTO;
import org.dromara.aimer.service.enums.PolarisConfigEnum;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author bixiu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class MybatisPlusGeneratorConfig extends AbstractPolarisConfig {

    @NotNull
    private DataSourceConfigDTO dataSourceConfig;

    @NotNull
    private GlobalConfigDTO globalConfig;

    @NotNull
    private PackageConfigDTO packageConfig;

    @NotNull
    private StrategyConfigDTO strategyConfig;

    @JsonIgnore
    public DataSourceConfig getTargetDataSourceConfig() {
        if (Objects.isNull(dataSourceConfig)) {
            return null;
        }
        return new DataSourceConfig.Builder(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword())
                .typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    // 兼容旧版本转换成Integer
                    if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);
                })
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
                .entityBuilder().superClass(BaseDO.class).enableFileOverride().formatFileName("%sDO").addIgnoreColumns("id", "create_time", "update_time", "deleted").enableTableFieldAnnotation().enableLombok().enableChainModel().build()
                .controllerBuilder().disable().build()
                .mapperBuilder().superClass(BaseMapperPlus.class).enableFileOverride().build()
                .serviceBuilder().superServiceClass(BaseServicePlus.class).enableFileOverride().build();
    }

    @Override
    String fileName() {
        return PolarisConfigEnum.MYBATIS_PLUS_GENERATOR_CONFIG.getPolarisKey();
    }
}
