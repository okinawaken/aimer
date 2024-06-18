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
 * 对象存储配置表
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_oss_config")
public class SysOssConfigDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 主建
     */
    @TableId("oss_config_id")
    private Long ossConfigId;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 配置key
     */
    @TableField("config_key")
    private String configKey;

    /**
     * accessKey
     */
    @TableField("access_key")
    private String accessKey;

    /**
     * 秘钥
     */
    @TableField("secret_key")
    private String secretKey;

    /**
     * 桶名称
     */
    @TableField("bucket_name")
    private String bucketName;

    /**
     * 前缀
     */
    @TableField("prefix")
    private String prefix;

    /**
     * 访问站点
     */
    @TableField("endpoint")
    private String endpoint;

    /**
     * 自定义域名
     */
    @TableField("domain")
    private String domain;

    /**
     * 是否https（Y=是,N=否）
     */
    @TableField("is_https")
    private String isHttps;

    /**
     * 域
     */
    @TableField("region")
    private String region;

    /**
     * 桶权限类型(0=private 1=public 2=custom)
     */
    @TableField("access_policy")
    private String accessPolicy;

    /**
     * 是否默认（0=是,1=否）
     */
    @TableField("status")
    private String status;

    /**
     * 扩展字段
     */
    @TableField("ext1")
    private String ext1;

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

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
