package org.dromara.aimer.auth.service.auth.strategy;


import org.dromara.aimer.api.enums.LoginTypeEnum;
import org.dromara.aimer.api.dto.ClientDTO;
import org.dromara.aimer.api.dto.LoginBodyDTO;

/**
 * 授权策略
 *
 * @author bixiu
 */
public interface IAuthStrategy {

    /**
     * 登录方法
     *
     * @param loginBody 登录参数
     * @param client    客户端类型
     * @return 登录参数
     */
    LoginBodyDTO login(LoginBodyDTO loginBody, ClientDTO client);


    /**
     * 参考枚举值：org.dromara.aimer.service.enums.LoginTypeEnum
     *
     * @return 支持的登录类型
     */
    LoginTypeEnum supportLoginType();
}
