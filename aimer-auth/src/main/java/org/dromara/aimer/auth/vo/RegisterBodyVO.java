package org.dromara.aimer.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import static org.dromara.aimer.common.constants.UserConstant.PASSWORD_MAX_LENGTH;
import static org.dromara.aimer.common.constants.UserConstant.PASSWORD_MIN_LENGTH;
import static org.dromara.aimer.common.constants.UserConstant.USERNAME_MAX_LENGTH;
import static org.dromara.aimer.common.constants.UserConstant.USERNAME_MIN_LENGTH;


/**
 * 用户注册对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBodyVO {

    /**
     * 用户名
     */
    @NotBlank(message = "{user.username.not.blank}")
    @Length(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = "{user.username.length.valid}")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "{user.password.not.blank}")
    @Length(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "{user.password.length.valid}")
    private String password;

    /**
     * 客户端id
     */
    @NotBlank(message = "{auth.clientid.not.blank}")
    private String clientId;

    /**
     * 授权类型
     */
    @NotBlank(message = "{auth.grant.type.not.blank}")
    private String grantType;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 租户ID
     */
    private String tenantId;
}
