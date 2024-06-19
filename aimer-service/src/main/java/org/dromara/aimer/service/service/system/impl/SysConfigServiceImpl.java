package org.dromara.aimer.service.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.aimer.repository.domain.SysConfigDO;
import org.dromara.aimer.repository.mapper.SysConfigMapper;
import org.dromara.aimer.service.service.system.ISysConfigService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigDO> implements ISysConfigService {

    @Override
    public String querySystemConfigByConfigKey(String configKey) {
        Optional<SysConfigDO> sysConfigDO = this.lambdaQuery()
                .eq(SysConfigDO::getConfigKey, configKey)
                .oneOpt();
        return sysConfigDO.map(SysConfigDO::getConfigValue).orElse(null);
    }
}
