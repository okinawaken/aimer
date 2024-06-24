package org.dromara.aimer.service.service.system;

import org.dromara.aimer.api.dto.ClientDTO;
import org.dromara.aimer.repository.base.BaseServicePlus;
import org.dromara.aimer.repository.domain.SysClientDO;

/**
 * <p>
 * 系统授权表 服务类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
public interface ISysClientService extends BaseServicePlus<SysClientDO> {

    /**
     * 通过客户端id查询客户端详情
     *
     * @param clientId 客户端id
     * @return 客户端详情
     */
    ClientDTO queryByClientId(String clientId);
}
