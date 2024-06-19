package org.dromara.aimer.service.service.system.impl;

import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.repository.domain.SysConfigDO;
import org.dromara.aimer.service.internal.generator.ISysConfigService;
import org.dromara.aimer.service.service.system.ISystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SystemConfigServiceImpl implements ISystemConfigService {

    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    public String querySystemConfigByConfigKey(String configKey) {
        Optional<SysConfigDO> sysConfigDO = sysConfigService.lambdaQuery()
                .eq(SysConfigDO::getConfigKey, configKey)
                .oneOpt();
        return sysConfigDO.map(SysConfigDO::getConfigValue).orElse(null);
    }
}
