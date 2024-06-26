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
 * 参数配置表
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_config")
public class SysConfigDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 参数键名
     */
    @TableField("config_key")
    private String configKey;

    /**
     * 参数键值
     */
    @TableField("config_value")
    private String configValue;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
