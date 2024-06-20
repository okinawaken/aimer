package org.dromara.aimer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统配置常量
 *
 * @author bixiu
 */
@Getter
@AllArgsConstructor
public enum SystemConfigEnum {

    /**
     * 系统注册用户开关
     */
    SYS_ACCOUNT_REGISTER_USER("sys.account.registerUser", "系统注册用户开关"),
    ;

    private final String configKey;

    private final String desc;
}
