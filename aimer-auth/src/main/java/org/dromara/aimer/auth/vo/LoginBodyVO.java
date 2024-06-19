package org.dromara.aimer.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginBodyVO {

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
     * 租户ID
     */
    private String tenantId;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;
}
