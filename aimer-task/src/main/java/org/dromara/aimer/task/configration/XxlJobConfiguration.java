package org.dromara.aimer.task.configration;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.dromara.aimer.service.config.XxlJobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 * @author bixiu
 */
@Configuration
public class XxlJobConfiguration {

    @Value("${spring.application.name}")
    private String appname;

    @Autowired
    private XxlJobConfig xxlJobConfig;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobConfig.getAddress());
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setAccessToken(xxlJobConfig.getAccessToken());
        return xxlJobSpringExecutor;
    }
}