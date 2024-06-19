package org.dromara.aimer.api.dubbo;

public interface ISystemConfigGrpcService {

    /**
     * 获取注册开关
     * @param tenantId 租户id
     * @return true开启，false关闭
     */
    boolean selectRegisterEnabled(String tenantId);
}
