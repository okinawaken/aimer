package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveCancelClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveEnterClientRespDTO;

public interface StudyRoomLeaveService {

    /**
     * 进入请假页。
     */
    StudyRoomUserLeaveEnterClientRespDTO userLeaveEnter(Long userPackageId);

    /**
     * 确认请假。
     */
    Integer userLeaveConfirm(StudyRoomUserLeaveConfirmClientReqDTO form);

    /**
     * 取消请假。
     */
    Integer userLeaveCancel(StudyRoomUserLeaveCancelClientReqDTO form);
}
