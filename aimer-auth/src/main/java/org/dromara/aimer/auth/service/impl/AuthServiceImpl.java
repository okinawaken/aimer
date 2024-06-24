package org.dromara.aimer.auth.service.impl;

import org.apache.commons.lang3.BooleanUtils;
import org.dromara.aimer.auth.dto.RegisterBodyDTO;
import org.dromara.aimer.auth.service.IAuthService;
import org.dromara.aimer.common.exception.UserException;
import org.dromara.aimer.integration.aimer.SystemConfigDubboRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bixiu
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private SystemConfigDubboRpcService systemConfigDubboRpcService;

    @Override
    public void register(RegisterBodyDTO registerBodyDTO) {
        Boolean isRegisterEnable = systemConfigDubboRpcService.queryRegisterEnable(registerBodyDTO.getTenantId());
        if (BooleanUtils.isNotFalse(isRegisterEnable)) {
            throw new UserException("当前系统注册未开启");
        }
    }
}
