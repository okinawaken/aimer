package org.dromara.aimer.service.service.dubbo.impl;

import org.dromara.aimer.api.dubbo.ISystemConfigRpcService;
import org.dromara.aimer.common.response.BaseResponse;
import org.dromara.aimer.service.service.system.ISystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SystemConfigRpcServiceImpl implements ISystemConfigRpcService {

    @Autowired
    private ISystemConfigService systemConfigService;

    @Override
    public BaseResponse<Boolean> selectRegisterEnabled(String tenantId) {
        return BaseResponse.ok();
    }
}
