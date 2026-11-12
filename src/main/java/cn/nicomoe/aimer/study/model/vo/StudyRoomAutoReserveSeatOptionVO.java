package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveSeatOptionDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 自动预约任务表单的区域 / 座位下拉选项。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomAutoReserveSeatOptionDTO.class)
public class StudyRoomAutoReserveSeatOptionVO {

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
