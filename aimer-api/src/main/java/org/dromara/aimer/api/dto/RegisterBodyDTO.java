package org.dromara.aimer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bixiu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBodyDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 租户ID
     */
    private String tenantId;
}
