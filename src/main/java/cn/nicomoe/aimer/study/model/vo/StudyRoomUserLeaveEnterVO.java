package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveEnterClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 进入请假页（/mp/user/userLeave/enterLeave）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomUserLeaveEnterClientRespDTO.class)
public class StudyRoomUserLeaveEnterVO {

    /**
     * 用户套餐ID
     */
    private Long userPackageId;

    /**
     * 是否可请假：0 不可
     */
    private Integer canLeaveState;

    /**
     * 不可请假原因
     */
    private String canNotLeaveMsg;

    /**
     * 剩余可请假天数
     */
    private Integer leftLeaveDays;

    /**
     * 今日使用状态
     */
    private Integer todayStudyState;

    /**
     * 请假记录
     */
    private List<StudyRoomUserLeaveVO> leaveList;
}
