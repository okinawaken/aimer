package org.dromara.aimer.service.service.dubbo.impl;

import org.dromara.aimer.api.dto.ClientDTO;
import org.dromara.aimer.api.dubbo.ISystemClientRpcService;
import org.dromara.aimer.common.response.BaseResponse;
import org.dromara.aimer.service.service.system.ISysClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bixiu
 */
@Service
public class SystemClientRpcServiceImpl implements ISystemClientRpcService {

    @Autowired
    private ISysClientService sysClientService;

    @Override
    public BaseResponse<ClientDTO> queryByClientId(String clientId) {
        ClientDTO clientDTO = sysClientService.queryByClientId(clientId);
        return BaseResponse.ok(clientDTO);
    }
}
