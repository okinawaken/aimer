package org.dromara.aimer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemConfigEnum {

    SYS_ACCOUNT_REGISTER_USER("sys.account.registerUser" , "系统注册用户开关"),
    ;

    private final String configKey;

    private final String desc;
}
