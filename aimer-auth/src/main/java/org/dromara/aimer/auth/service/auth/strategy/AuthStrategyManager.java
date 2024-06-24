package org.dromara.aimer.auth.service.auth.strategy;

import org.dromara.aimer.api.enums.LoginTypeEnum;
import org.dromara.aimer.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author bixiu
 */
@Component
public class AuthStrategyManager {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<LoginTypeEnum, IAuthStrategy> STRATEGY_MAP = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, IAuthStrategy> beansOfType = applicationContext.getBeansOfType(IAuthStrategy.class);
        for (IAuthStrategy authStrategy : beansOfType.values()) {
            STRATEGY_MAP.put(authStrategy.supportLoginType(), authStrategy);
        }
    }

    public IAuthStrategy getAuthStrategy(String loinType) {
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getByLoginType(loinType);
        IAuthStrategy authStrategy = STRATEGY_MAP.get(loginTypeEnum);
        if (Objects.isNull(authStrategy)) {
            throw new ServiceException("Auth strategy '" + loinType + "' not found");
        }
        return authStrategy;
    }
}
