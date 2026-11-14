package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairSubmitClientReqDTO;

public interface StudyRoomRepairService {

    /** 提交报修。 */
    void submitRepair(StudyRoomRepairSubmitClientReqDTO form);

    /** 报修列表。 */
    StudyRoomRepairListClientRespDTO repairList();

    /** 报修详情。 */
    StudyRoomRepairClientRespDTO repairDetail(Long id);
}
