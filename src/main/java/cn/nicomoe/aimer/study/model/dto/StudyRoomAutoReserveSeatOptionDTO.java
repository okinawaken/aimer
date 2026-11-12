package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/**
 * 自动预约任务表单的区域 / 座位下拉选项。
 * <p>
 * 由门店座位图展平而来，一行代表一个座位：区域信息用于构建区域下拉框，
 * 座位信息用于构建座位下拉框（按 {@code areaId} 联动过滤）。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomAutoReserveSeatOptionDTO {

    /**
     * 区域 ID
     */
    private Long areaId;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 座位 ID，保存任务时作为 seatId
     */
    private Long seatId;

    /**
     * 座位号，如 独处位P10·有帘
     */
    private String seatNumber;

    /**
     * 座位状态：10 空闲 20 部分时段可约 30 不可预约
     */
    private Integer seatReserveState;

    /**
     * 状态描述（展示用）
     */
    private String seatReserveDesc;
}
