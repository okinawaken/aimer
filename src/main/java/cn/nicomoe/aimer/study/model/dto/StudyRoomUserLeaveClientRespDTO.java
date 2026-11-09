package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 请假记录
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomUserLeaveClientRespDTO {

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
