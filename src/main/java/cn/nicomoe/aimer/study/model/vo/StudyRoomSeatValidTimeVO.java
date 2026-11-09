package cn.nicomoe.aimer.study.model.vo;


import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatValidTimeClientRespDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalTime;

/**
 * 座位可预约时段（StudyRoomSeatVO.validTimeStrList 元素）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomSeatValidTimeClientRespDTO.class)
public class StudyRoomSeatValidTimeVO {

    /**
     * 可预约开始时间 HH:mm
     */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime validStartTimeStr;

    /**
     * 可预约结束时间 HH:mm
     */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime validEndTimeStr;

    /**
     * 可预约时长描述，如 3小时50分钟
     */
    private String validMinuteStr;

    /**
     * 可预约时段，如 06:00-09:50
     */
    private String validTimeStr;
}
