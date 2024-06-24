package org.dromara.aimer.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dromara.aimer.common.exception.ServiceException;

/**
 * @author bixiu
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    /**
     * 账号密码
     */
    PASSWORD,

    /**
     * 邮件验证
     */
    EMAIL,
    ;

    public static LoginTypeEnum getByLoginType(String loginType) {
        for (LoginTypeEnum loginTypeEnum : LoginTypeEnum.values()) {
            if (loginTypeEnum.name().equals(loginType)) {
                return loginTypeEnum;
            }
        }
        throw new ServiceException("Invalid login type: " + loginType);
    }
}
