package org.dromara.aimer.api.dubbo;

import org.dromara.aimer.api.dto.ClientDTO;
import org.dromara.aimer.common.response.BaseResponse;

/**
 * @author bixiu
 */
public interface ISystemClientRpcService {

    /**
     * 通过客户端id查询客户端详情
     *
     * @param clientId 客户端id
     * @return 客户端详情
     */
    BaseResponse<ClientDTO> queryByClientId(String clientId);
}
