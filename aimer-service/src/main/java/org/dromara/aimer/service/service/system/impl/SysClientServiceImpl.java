package org.dromara.aimer.service.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.aimer.api.dto.ClientDTO;
import org.dromara.aimer.repository.domain.SysClientDO;
import org.dromara.aimer.repository.mapper.SysClientMapper;
import org.dromara.aimer.service.service.system.ISysClientService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统授权表 服务实现类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Service
public class SysClientServiceImpl extends ServiceImpl<SysClientMapper, SysClientDO> implements ISysClientService {

    @Override
    public ClientDTO queryByClientId(String clientId) {
        return this.getById(clientId, ClientDTO.class);
    }
}
