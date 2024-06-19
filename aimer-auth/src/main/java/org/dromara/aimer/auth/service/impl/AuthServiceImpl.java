package org.dromara.aimer.auth.service.impl;

import org.dromara.aimer.api.dubbo.ISystemConfigRpcService;
import org.dromara.aimer.auth.dto.RegisterBodyDTO;
import org.dromara.aimer.auth.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private ISystemConfigRpcService systemConfigRpcService;

    @Override
    public void register(RegisterBodyDTO registerBodyDTO) {
        systemConfigRpcService.selectRegisterEnabled(registerBodyDTO.getTenantId());
    }
}
