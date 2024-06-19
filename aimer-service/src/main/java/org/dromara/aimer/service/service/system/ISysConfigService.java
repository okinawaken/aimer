package org.dromara.aimer.service.service.system;

import org.dromara.aimer.repository.base.BaseServicePlus;
import org.dromara.aimer.repository.domain.SysConfigDO;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
public interface ISysConfigService extends BaseServicePlus<SysConfigDO> {

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String querySystemConfigByConfigKey(String configKey);
}
