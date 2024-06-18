package org.dromara.aimer.service.config;

import com.tencent.cloud.polaris.context.config.PolarisContextProperties;
import com.tencent.polaris.configuration.api.core.ConfigFile;
import com.tencent.polaris.configuration.api.core.ConfigFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Slf4j
public abstract class AbstractPolarisConfig extends AbstractConfig {

    @Autowired
    private ConfigFileService configFileService;

    @Autowired
    private Environment environment;

    @Autowired
    private PolarisContextProperties polarisContextProperties;

    @PostConstruct
    public void init() {
        ConfigFile configFile = configFileService.getConfigFile(namespace(), group(), fileName());
        refreshConfig(configFile.getContent());

        configFile.addChangeListener(event -> {
            String newValue = event.getNewValue();
            refreshConfig(newValue);
        });
    }

    @Override
    String namespace() {
        return polarisContextProperties.getNamespace();
    }

    @Override
    String group() {
        return StringUtils.getIfBlank(polarisContextProperties.getService(),
                () -> environment.getProperty("spring.application.name"));
    }
}
