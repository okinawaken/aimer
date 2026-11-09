package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 请假记录
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomUserLeaveClientRespDTO.class)
public class StudyRoomUserLeaveVO {

    /**
     * 请假记录ID
     */
    private Long id;

    /**
     * 请假开始日期字符串
     */
    private String leaveStartTimeStr;

    /**
     * 请假实际结束日期字符串
     */
    private String leaveRealEndTimeStr;

    /**
     * 请假天数说明
     */
    private String leaveDaysStr;
}
