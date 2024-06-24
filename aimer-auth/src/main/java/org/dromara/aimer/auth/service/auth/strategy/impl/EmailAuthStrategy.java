package org.dromara.aimer.auth.service.auth.strategy.impl;

import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.api.dto.ClientDTO;
import org.dromara.aimer.api.dto.LoginBodyDTO;
import org.dromara.aimer.api.enums.LoginTypeEnum;
import org.dromara.aimer.auth.service.auth.strategy.IAuthStrategy;
import org.springframework.stereotype.Component;

/**
 * @author bixiu
 */
@Slf4j
@Component
public class EmailAuthStrategy implements IAuthStrategy {
    @Override
    public LoginBodyDTO login(LoginBodyDTO loginBody, ClientDTO client) {
        return null;
    }

    @Override
    public LoginTypeEnum supportLoginType() {
        return LoginTypeEnum.PASSWORD;
    }
}
