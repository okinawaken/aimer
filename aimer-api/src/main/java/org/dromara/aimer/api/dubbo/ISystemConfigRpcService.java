package org.dromara.aimer.api.dubbo;

import org.dromara.aimer.common.response.BaseResponse;

/**
 * @author bixiu
 */
public interface ISystemConfigRpcService {

    /**
     * 获取注册开关
     *
     * @param tenantId 租户id
     * @return true开启，false关闭
     */
    BaseResponse<Boolean> selectRegisterEnabled(String tenantId);
}
