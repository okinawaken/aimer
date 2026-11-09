package cn.nicomoe.aimer.study.model.dto;

import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoReserveTaskConfigBO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/**
 * 自动预约任务创建 / 修改请求
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomAutoReserveTaskConfigBO.class)
public class StudyRoomAutoReserveTaskReqDTO {

    /**
     * 门店 ID
     */
    @NotNull(message = "门店不能为空")
    private Integer venueId;

    /**
     * 指定座位ID，不指定传 0 或不传
     */
    private Long seatId;

    /**
     * 指定座位编号（展示用）
     */
    private String seatNumber;

    /**
     * 偏好区域ID，不指定传 0 或不传
     */
    private Long areaId;

    /**
     * 偏好区域名称（展示用）
     */
    private String areaName;

    /**
     * 期望开始时间 {@code HH:mm}
     */
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime reserveStartTime;

    /**
     * 期望结束时间 {@code HH:mm}
     */
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime reserveEndTime;

    /**
     * 执行星期，约定 1 周一 … 7 周日（当前未做取值校验）
     */
    @NotEmpty(message = "执行星期不能为空")
    private List<Integer> weekDays;
}
