package cn.nicomoe.aimer.study.client.http.v1;

import cn.nicomoe.aimer.study.client.http.StudyRoomBaseClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveCancelClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveEnterClientRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

/**
 * 请假域：套餐请假
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomLeaveClient extends StudyRoomBaseClient {

    /**
     * 进入请假页（校验可请假套餐）
     *
     * @param userPackageId 参数 userPackageId
     * @return 响应数据
     */
    @Get(url = "/mp/user/userLeave/enterLeave")
    StudyRoomClientResult<StudyRoomUserLeaveEnterClientRespDTO> userLeaveEnter(@Query(value = "userPackageId") Long userPackageId);

    /**
     * 确认请假
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/user/userLeave/confirmLeave")
    StudyRoomClientResult<Integer> userLeaveConfirm(@JSONBody StudyRoomUserLeaveConfirmClientReqDTO form);

    /**
     * 取消请假
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/user/userLeave/cancelLeave")
    StudyRoomClientResult<Integer> userLeaveCancel(@JSONBody StudyRoomUserLeaveCancelClientReqDTO form);

}
