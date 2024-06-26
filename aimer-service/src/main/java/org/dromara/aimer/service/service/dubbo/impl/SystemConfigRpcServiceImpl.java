package org.dromara.aimer.service.service.dubbo.impl;

import cn.hutool.core.convert.Convert;
import org.dromara.aimer.api.dubbo.ISystemConfigRpcService;
import org.dromara.aimer.service.enums.SystemConfigEnum;
import org.dromara.aimer.common.response.BaseResponse;
import org.dromara.aimer.repository.domain.SysConfigDO;
import org.dromara.aimer.service.service.system.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @author bixiu
 */
@Service
public class SystemConfigRpcServiceImpl implements ISystemConfigRpcService {

    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    public BaseResponse<Boolean> selectRegisterEnabled(String tenantId) {
        Optional<SysConfigDO> sysConfigDO = sysConfigService.lambdaQuery()
                .eq(SysConfigDO::getConfigKey, SystemConfigEnum.SYS_ACCOUNT_REGISTER_USER.getConfigKey())
                .oneOpt();
        Boolean result = sysConfigDO.map(SysConfigDO::getConfigValue)
                .map(Convert::toBool)
                .orElse(Boolean.FALSE);
        return BaseResponse.ok(result);
    }
}
