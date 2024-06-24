package org.dromara.aimer.auth.service.auth;

import org.dromara.aimer.api.dto.RegisterBodyDTO;

/**
 * @author bixiu
 */
public interface IAuthService {

    /**
     * 注册用户
     *
     * @param registerBodyDTO 注册体
     */
    void register(RegisterBodyDTO registerBodyDTO);
}
