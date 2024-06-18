package org.dromara.aimer.repository.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_client")
public class SysClientDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;

    /**
     * 客户端id
     */
    @TableField("client_id")
    private String clientId;

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
     * token活跃超时时间
     */
    @TableField("active_timeout")
    private Integer activeTimeout;

    /**
     * token固定超时
     */
    @TableField("timeout")
    private Integer timeout;

    /**
     * 状态（0正常 1停用）
     */
    @TableField("status")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 创建部门
     */
    @TableField("create_dept")
    private Long createDept;

    /**
     * 创建者
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新者
     */
    @TableField("update_by")
    private Long updateBy;
}
