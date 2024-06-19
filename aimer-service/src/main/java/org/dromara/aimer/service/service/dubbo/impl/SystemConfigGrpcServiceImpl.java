package org.dromara.aimer.service.service.dubbo.impl;

import org.dromara.aimer.api.dubbo.ISystemConfigGrpcService;
import org.dromara.aimer.service.service.system.ISystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SystemConfigGrpcServiceImpl implements ISystemConfigGrpcService {

    @Autowired
    private ISystemConfigService systemConfigService;

    @Override
    public boolean selectRegisterEnabled(String tenantId) {
        return false;
    }
}
