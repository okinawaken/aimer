package org.dromara.aimer.service.config;

import com.tencent.polaris.configuration.api.core.ConfigFile;
import com.tencent.polaris.configuration.api.core.ConfigFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Slf4j
public abstract class AbstractPolarisConfig extends AbstractConfig {

    @Autowired
    private ConfigFileService configFileService;

    @PostConstruct
    public void init() {
        ConfigFile configFile = configFileService.getConfigFile(namespace, fileGroup, fileName());
        refreshConfig(configFile.getContent());

        configFile.addChangeListener(event -> {
            String newValue = event.getNewValue();
            refreshConfig(newValue);
        });
    }
}
