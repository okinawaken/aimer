package org.dromara.aimer.service.service.system;

public interface ISystemConfigService {

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String querySystemConfigByConfigKey(String configKey);
    
}
