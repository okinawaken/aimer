package org.dromara.aimer.integration.aimer;

import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.api.dto.ClientDTO;
import org.dromara.aimer.api.dubbo.ISystemClientRpcService;
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
public class SystemClientDubboRpcService {

    @Autowired
    @Qualifier("systemClientRpcService")
    private ISystemClientRpcService systemClientRpcService;

    public ClientDTO queryByClientId(String clientId) {
        try {
            BaseResponse<ClientDTO> response = systemClientRpcService.queryByClientId(clientId);
            log.info("systemClientRpcService queryByClientId, clientId:{}, response:{}", clientId, JsonUtils.toJson(response));
            if (Objects.isNull(response)
                    || Objects.isNull(response.getData())
                    || BaseResponse.isError(response)) {
                log.error("systemClientRpcService queryByClientId, clientId:{}, response has error", clientId, new RpcException("response has error"));
                return null;
            }
            return response.getData();
        } catch (Exception e) {
            log.error("systemClientRpcService queryByClientId, clientId:{}", clientId, new RpcException(e));
            return null;
        }
    }
}
