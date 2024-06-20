package org.dromara.aimer.auth.service;

import org.dromara.aimer.auth.dto.RegisterBodyDTO;

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
