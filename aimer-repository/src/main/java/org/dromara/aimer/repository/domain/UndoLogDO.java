package org.dromara.aimer.repository.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import org.dromara.aimer.repository.base.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * AT transaction mode undo table
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("undo_log")
public class UndoLogDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * branch transaction id
     */
    @TableField("branch_id")
    private Long branchId;

    /**
     * global transaction id
     */
    @TableField("xid")
    private String xid;

    /**
     * undo_log context,such as serialization
     */
    @TableField("context")
    private String context;

    /**
     * rollback info
     */
    @TableField("rollback_info")
    private byte[] rollbackInfo;

    /**
     * 0:normal status,1:defense status
     */
    @TableField("log_status")
    private Integer logStatus;

    /**
     * create datetime
     */
    @TableField("log_created")
    private LocalDateTime logCreated;

    /**
     * modify datetime
     */
    @TableField("log_modified")
    private LocalDateTime logModified;
}
