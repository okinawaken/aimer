package org.dromara.aimer.repository.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import org.dromara.aimer.repository.base.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 租户表
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_tenant")
public class SysTenantDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 联系人
     */
    @TableField("contact_user_name")
    private String contactUserName;

    /**
     * 联系电话
     */
    @TableField("contact_phone")
    private String contactPhone;

    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 统一社会信用代码
     */
    @TableField("license_number")
    private String licenseNumber;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 企业简介
     */
    @TableField("intro")
    private String intro;

    /**
     * 域名
     */
    @TableField("domain")
    private String domain;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 租户套餐编号
     */
    @TableField("package_id")
    private Long packageId;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;

    /**
     * 用户数量（-1不限制）
     */
    @TableField("account_count")
    private Integer accountCount;

    /**
     * 租户状态（0正常 1停用）
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
