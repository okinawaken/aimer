package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/**
 * 确认请假（{@code /mp/user/userLeave/confirmLeave}），日期格式 {@code yyyy-M-d}（月、日不补零）。
 */
@Data
public class StudyRoomUserLeaveConfirmClientReqDTO {

    /**
     * 用户套餐ID（必填）
     */
    private Long userPackageId;

    /**
     * 请假开始日期 {@code yyyy-M-d}（必填）
     */
    private String leaveStartTimeStr;

    /**
     * 请假结束日期 {@code yyyy-M-d}（必填）
     */
    private String leaveEndTimeStr;

    /**
     * 请假原因（必填）
     */
    private String leaveReason;
}
