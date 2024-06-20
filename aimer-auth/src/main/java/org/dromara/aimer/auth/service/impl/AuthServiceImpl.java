package org.dromara.aimer.auth.service.impl;

import org.apache.commons.lang3.BooleanUtils;
import org.dromara.aimer.api.dubbo.ISystemConfigRpcService;
import org.dromara.aimer.auth.dto.RegisterBodyDTO;
import org.dromara.aimer.auth.service.IAuthService;
import org.dromara.aimer.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bixiu
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private ISystemConfigRpcService systemConfigRpcService;

    @Override
    public void register(RegisterBodyDTO registerBodyDTO) {
        BaseResponse<Boolean> registerEnabled = systemConfigRpcService.selectRegisterEnabled(registerBodyDTO.getTenantId());
        if (BooleanUtils.isNotTrue(registerEnabled.getData())) {
            return;
        }

    }
}
