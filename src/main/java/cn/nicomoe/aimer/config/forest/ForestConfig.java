package cn.nicomoe.aimer.config.forest;

import com.dtflys.forest.converter.json.ForestJacksonConverter;
import com.dtflys.forest.springboot.annotation.ForestScan;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Forest 客户端扫描配置
 *
 * @author bixiu
 * @since 4.2.0
 */
@Configuration
@ForestScan(basePackages = "cn.nicomoe.aimer.*.client.http")
public class ForestConfig {

    /**
     * 带 Java 8 时间支持的 Forest Jackson 转换器。
     *
     * @return Forest JSON 转换器
     */
    @Bean
    public ForestJacksonConverter forestJacksonConverter() {
        ForestJacksonConverter converter = new ForestJacksonConverter();
        converter.getMapper().registerModule(new JavaTimeModule());
        converter.getMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return converter;
    }
}
