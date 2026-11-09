package cn.nicomoe.aimer.study.model.query;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 自动预约执行记录查询条件
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomAutoReserveTaskRecordQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务 ID
     */
    private Long taskId;

    /**
     * 系统用户 ID
     */
    private Long userId;

    /**
     * 触发方式：0 定时，1 手动
     */
    private Integer triggerType;

    /**
     * 目标预约日期
     */
    private LocalDate targetDate;

    /**
     * 执行状态：0 跳过，1 成功，2 失败
     */
    private Integer status;
}
