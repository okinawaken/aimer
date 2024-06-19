package org.dromara.aimer.auth.service;

import org.dromara.aimer.auth.dto.RegisterBodyDTO;

public interface IAuthService {

    void register(RegisterBodyDTO registerBodyDTO);
}
