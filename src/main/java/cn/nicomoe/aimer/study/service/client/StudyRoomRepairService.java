package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairSubmitClientReqDTO;

/**
 * 自习室报修域服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomRepairService {

    /**
     * 提交报修。
     *
     * @param form 请求参数
     */
    void submitRepair(StudyRoomRepairSubmitClientReqDTO form);

    /**
     * 报修列表。
     *
     * @return 报修列表数据
     */
    StudyRoomRepairListClientRespDTO repairList();

    /**
     * 报修详情。
     *
     * @param id ID
     * @return 报修详情数据
     */
    StudyRoomRepairClientRespDTO repairDetail(Long id);
}
