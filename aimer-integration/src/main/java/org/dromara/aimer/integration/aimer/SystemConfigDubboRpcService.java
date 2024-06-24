package org.dromara.aimer.integration.aimer;

import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.api.dubbo.ISystemConfigRpcService;
import org.dromara.aimer.common.exception.RpcException;
import org.dromara.aimer.common.response.BaseResponse;
import org.dromara.aimer.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author bixiu
 */
@Slf4j
@Service
public class SystemConfigDubboRpcService {

    @Autowired
    @Qualifier("systemConfigRpcService")
    private ISystemConfigRpcService systemConfigRpcService;

    public Boolean queryRegisterEnable(String tenantId) {
        try {
            BaseResponse<Boolean> baseResponse = systemConfigRpcService.selectRegisterEnabled(tenantId);
            log.info("SystemConfigDubboRpcService queryRegisterEnable, tenantId:{}, baseResponse:{}", tenantId, JsonUtils.toJson(baseResponse));
            if (Objects.isNull(baseResponse)
                    || Objects.isNull(baseResponse.getData())
                    || BaseResponse.isError(baseResponse)) {
                log.error("SystemConfigDubboRpcService queryRegisterEnable, tenantId:{}, response has error", tenantId, new RpcException("response has error"));
                return Boolean.FALSE;
            }
            return baseResponse.getData();
        } catch (Exception e) {
            log.error("SystemConfigDubboRpcService queryRegisterEnable, tenantId:{}", tenantId, new RpcException(e));
            return Boolean.FALSE;
        }
    }
}
