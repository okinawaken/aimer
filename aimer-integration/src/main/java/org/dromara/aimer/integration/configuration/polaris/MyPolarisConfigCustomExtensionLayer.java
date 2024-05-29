package org.dromara.aimer.integration.configuration.polaris;

import com.tencent.cloud.polaris.config.adapter.PolarisConfigCustomExtensionLayer;
import com.tencent.cloud.polaris.config.adapter.PolarisConfigPropertyAutoRefresher;
import com.tencent.cloud.polaris.config.adapter.PolarisPropertySource;
import com.tencent.cloud.polaris.config.adapter.PolarisPropertySourceManager;
import com.tencent.polaris.configuration.api.core.ConfigFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;

@Slf4j
public class MyPolarisConfigCustomExtensionLayer implements PolarisConfigCustomExtensionLayer {
    @Override
    public void initRegisterConfig(PolarisConfigPropertyAutoRefresher polarisConfigPropertyAutoRefresher) {
        log.info("MyPolarisConfigCustomExtensionLayer.initRegisterConfig");
    }

    @Override
    public void initConfigFiles(Environment environment, CompositePropertySource compositePropertySource, PolarisPropertySourceManager polarisPropertySourceManager, ConfigFileService configFileService) {
        log.info("MyPolarisConfigCustomExtensionLayer.initConfigFiles");
    }

    @Override
    public void executeAfterLocateConfigReturning(CompositePropertySource compositePropertySource) {
        log.info("MyPolarisConfigCustomExtensionLayer.executeAfterLocateConfigReturning");
    }

    @Override
    public boolean executeRegisterPublishChangeListener(PolarisPropertySource polarisPropertySource) {
        log.info("MyPolarisConfigCustomExtensionLayer.executeRegisterPublishChangeListener");
        return false;
    }
}
