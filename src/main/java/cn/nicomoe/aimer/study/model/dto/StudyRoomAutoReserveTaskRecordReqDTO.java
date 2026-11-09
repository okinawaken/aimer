package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 自动预约执行记录创建或修改请求
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomAutoReserveTaskRecordReqDTO {

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

    /**
     * 座位ID（成功时为实际预约座位，跳过时可能只是选中的座位）
     */
    private Long seatId;

    /**
     * 座位编号（成功时为实际预约座位，跳过时可能只是选中的座位）
     */
    private String seatNumber;

    /**
     * 任务期望预约时段 {@code HH:mm-HH:mm}
     */
    private String timeRange;

    /**
     * 上游预约单ID
     */
    private Long userReserveId;

    /**
     * 跳过或失败原因描述
     */
    private String failMsg;
}
