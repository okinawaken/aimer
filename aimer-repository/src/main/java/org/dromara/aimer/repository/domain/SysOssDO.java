package org.dromara.aimer.repository.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.dromara.aimer.repository.base.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * OSS对象存储表
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_oss")
public class SysOssDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 对象存储主键
     */
    @TableId("oss_id")
    private Long ossId;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 原名
     */
    @TableField("original_name")
    private String originalName;

    /**
     * 文件后缀名
     */
    @TableField("file_suffix")
    private String fileSuffix;

    /**
     * URL地址
     */
    @TableField("url")
    private String url;

    /**
     * 创建部门
     */
    @TableField("create_dept")
    private Long createDept;

    /**
     * 上传人
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 服务商
     */
    @TableField("service")
    private String service;
}
