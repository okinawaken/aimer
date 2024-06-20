package org.dromara.aimer.service.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.service.config.MybatisPlusGeneratorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author bixiu
 */
@Slf4j
@Component
public class MybatisPlusGeneratorUtils {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MybatisPlusGeneratorConfig mybatisPlusGeneratorConfig;

    public void generateTable() {
        new AutoGenerator(mybatisPlusGeneratorConfig.getTargetDataSourceConfig())
                .global(mybatisPlusGeneratorConfig.getTargetGlobalConfig())
                .packageInfo(mybatisPlusGeneratorConfig.getTargetPackageConfig())
                .strategy(mybatisPlusGeneratorConfig.getTargetStrategyConfig())
                .execute();
    }
}
