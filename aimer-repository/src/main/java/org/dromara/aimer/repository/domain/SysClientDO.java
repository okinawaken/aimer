package org.dromara.aimer.repository.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import org.dromara.aimer.repository.base.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统授权表
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_client")
public class SysClientDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端key
     */
    @TableField("client_key")
    private String clientKey;

    /**
     * 客户端秘钥
     */
    @TableField("client_secret")
    private String clientSecret;

    /**
     * 授权类型
     */
    @TableField("grant_type")
    private String grantType;

    /**
     * 设备类型
     */
    @TableField("device_type")
    private String deviceType;

    /**
     * token超时时间，单位ms
     */
    @TableField("timeout")
    private Integer timeout;

    /**
     * 状态，0、正常 1、停用
     */
    @TableField("status")
    private Integer status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
