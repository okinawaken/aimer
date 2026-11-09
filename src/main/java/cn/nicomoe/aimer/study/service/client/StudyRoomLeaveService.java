package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveCancelClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveEnterClientRespDTO;

/**
 * 自习室请假域服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomLeaveService {

    /**
     * 进入请假页。
     *
     * @param userPackageId 用户套餐 ID
     * @return 请假页数据
     */
    StudyRoomUserLeaveEnterClientRespDTO userLeaveEnter(Long userPackageId);

    /**
     * 确认请假。
     *
     * @param form 请求参数
     * @return 处理结果
     */
    Integer userLeaveConfirm(StudyRoomUserLeaveConfirmClientReqDTO form);

    /**
     * 取消请假。
     *
     * @param form 请求参数
     * @return 处理结果
     */
    Integer userLeaveCancel(StudyRoomUserLeaveCancelClientReqDTO form);
}
