package org.dromara.aimer.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import org.dromara.aimer.repository.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户与岗位关联表
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_user_post")
public class SysUserPostEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 岗位ID
     */
    @TableField("post_id")
    private Long postId;
}
