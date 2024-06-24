package org.dromara.aimer.auth.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.aimer.api.dto.RegisterBodyDTO;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import static org.dromara.aimer.common.constants.UserConstant.PASSWORD_MAX_LENGTH;
import static org.dromara.aimer.common.constants.UserConstant.PASSWORD_MIN_LENGTH;
import static org.dromara.aimer.common.constants.UserConstant.USERNAME_MAX_LENGTH;
import static org.dromara.aimer.common.constants.UserConstant.USERNAME_MIN_LENGTH;


/**
 * 用户注册对象
 *
 * @author bixiu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@AutoMapper(target = RegisterBodyDTO.class)
public class RegisterBodyVO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = "用户名长度必须在2-20位以内")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "密码必须在5-20位以内")
    private String password;

    /**
     * 用户类型
     */
    @NotBlank(message = "用户类型不能为空")
    private String userType;

    /**
     * 租户ID
     */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;
}
