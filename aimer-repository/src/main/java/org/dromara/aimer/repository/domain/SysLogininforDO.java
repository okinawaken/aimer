package org.dromara.aimer.repository.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.dromara.aimer.repository.base.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统访问记录
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_logininfor")
public class SysLogininforDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 访问ID
     */
    @TableId("info_id")
    private Long infoId;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 用户账号
     */
    @TableField("user_name")
    private String userName;

    /**
     * 客户端
     */
    @TableField("client_key")
    private String clientKey;

    /**
     * 设备类型
     */
    @TableField("device_type")
    private String deviceType;

    /**
     * 登录IP地址
     */
    @TableField("ipaddr")
    private String ipaddr;

    /**
     * 登录地点
     */
    @TableField("login_location")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @TableField("browser")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("os")
    private String os;

    /**
     * 登录状态（0成功 1失败）
     */
    @TableField("status")
    private String status;

    /**
     * 提示消息
     */
    @TableField("msg")
    private String msg;

    /**
     * 访问时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;
}
