package cn.nicomoe.aimer.study.client.http.v1;

import cn.nicomoe.aimer.study.client.http.StudyRoomBaseClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairSubmitClientReqDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Var;

/**
 * 报修域：提交报修、报修记录
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomRepairClient extends StudyRoomBaseClient {

    /**
     * 提交报修（实测成功时 {@code data} 为 {@code null}）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/userRepair/submit")
    StudyRoomClientResult<Void> submitRepair(@JSONBody StudyRoomRepairSubmitClientReqDTO form);

    /**
     * 报修列表
     *
     * @return 响应数据
     */
    @Get(url = "/mp/userRepair/list")
    StudyRoomClientResult<StudyRoomRepairListClientRespDTO> repairList();

    /**
     * 报修详情
     *
     * @param id ID
     * @return 响应数据
     */
    @Get(url = "/mp/userRepair/detail/${id}")
    StudyRoomClientResult<StudyRoomRepairClientRespDTO> repairDetail(@Var(value = "id") Long id);

}
