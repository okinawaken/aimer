package org.dromara.aimer.repository.utils;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.query.DefaultQuery;
import lombok.extern.slf4j.Slf4j;
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
                            .enableSwagger()
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd");
                })
                .packageConfig(builder ->
                        builder.parent("org.dromara.aimer")
                                .moduleName("repository")
                                .entity("entity")
                                .service("service")
                                .serviceImpl("service.impl")
                                .mapper("mapper")
                                .xml("mapper")
                                .controller("controller")
                                .pathInfo(MapUtil.<OutputFile, String>builder()
                                        .put(OutputFile.xml, "aimer-repository/src/main/resources")
                                        .build())
                )
                .strategyConfig(builder ->
                        builder.addInclude("t_simple") // 设置需要生成的表名
                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .execute();
    }
}
