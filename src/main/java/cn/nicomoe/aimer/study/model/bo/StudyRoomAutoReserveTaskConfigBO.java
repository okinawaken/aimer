package cn.nicomoe.aimer.study.model.bo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/** 自动预约任务规则详情（{@code task_json}）。 {@code seatId = 0} 表示不限座位，{@code areaId = 0} 表示不限区域；两者都未指定时随便约一个可约座位。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomAutoReserveTaskConfigBO {

    /** 指定座位ID */
    private Long seatId;

    /** 指定座位编号（展示用） */
    private String seatNumber;

    /** 偏好区域ID */
    private Long areaId;

    /** 偏好区域名称（展示用） */
    private String areaName;

    /** 期望开始时间 {@code HH:mm} */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime reserveStartTime;

    /** 期望结束时间 {@code HH:mm} */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime reserveEndTime;

    /** 执行星期，1 周一 … 7 周日 */
    private List<Integer> weekDays;
}
