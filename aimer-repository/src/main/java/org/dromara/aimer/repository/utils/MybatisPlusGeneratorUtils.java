package org.dromara.aimer.repository.utils;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.query.DefaultQuery;
import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.repository.base.BaseMapperPlus;
import org.dromara.aimer.repository.base.BaseServicePlus;
import org.dromara.aimer.repository.convert.MyTypeConvertHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
public class MybatisPlusGeneratorUtils {

    @Autowired
    private DataSource dataSource;

    public void generateTable() {
        FastAutoGenerator.create(new DataSourceConfig.Builder(dataSource))
                .dataSourceConfig(builder -> {
                    builder.keyWordsHandler(new MySqlKeyWordsHandler())
                            .typeConvertHandler(new MyTypeConvertHandler())
                            .databaseQueryClass(DefaultQuery.class);
                })
                .globalConfig(builder -> {
                    builder.disableOpenDir()
                            .outputDir("./")
                            .author("wangleijin")
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd");
                })
                .packageConfig(builder ->
                        builder.parent("org.dromara.aimer")
                                .entity("repository.entity")
                                .service("service.internal.generator")
                                .serviceImpl("service.internal.generator.impl")
                                .mapper("repository.mapper")
                                .xml("repository.mapper")
                                .controller("console.controller.generator")
                                .pathInfo(MapUtil.<OutputFile, String>builder()
                                        .put(OutputFile.entity, "./aimer-repository/src/main/java/org/dromara/aimer/repository/entity")
                                        .put(OutputFile.service, "./aimer-service/src/main/java/org/dromara/aimer/service/internal/generator")
                                        .put(OutputFile.serviceImpl, "./aimer-service/src/main/java/org/dromara/aimer/service/internal/generator/impl")
                                        .put(OutputFile.mapper, "./aimer-repository/src/main/java/org/dromara/aimer/repository/mapper")
                                        .put(OutputFile.xml, "./aimer-repository/src/main/resources/mapper")
                                        .put(OutputFile.controller, "./aimer-console/src/main/java/org/dromara/aimer/console/controller/generator")
                                        .build())
                )
                .strategyConfig(builder ->
                        builder.addInclude("user")
                                .entityBuilder().enableFileOverride().enableTableFieldAnnotation().enableLombok().enableChainModel().formatFileName("%sEntity")
                                .controllerBuilder().enableFileOverride().enableRestStyle().enableHyphenStyle()
                                .serviceBuilder().enableFileOverride().superServiceClass(BaseServicePlus.class)
                                .mapperBuilder().enableFileOverride().superClass(BaseMapperPlus.class)
                )
                .execute();
    }
}
