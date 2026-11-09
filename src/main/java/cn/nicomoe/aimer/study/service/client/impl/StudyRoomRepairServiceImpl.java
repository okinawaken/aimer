package cn.nicomoe.aimer.study.service.client.impl;

import cn.nicomoe.aimer.study.client.http.v1.StudyRoomRepairClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairSubmitClientReqDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomRepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自习室报修域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomRepairServiceImpl implements StudyRoomRepairService {

    private final StudyRoomRepairClient repairClient;

    @Override
    public void submitRepair(StudyRoomRepairSubmitClientReqDTO form) {
        repairClient.submitRepair(form);
    }

    @Override
    public StudyRoomRepairListClientRespDTO repairList() {
        StudyRoomClientResult<StudyRoomRepairListClientRespDTO> result = repairClient.repairList();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomRepairClientRespDTO repairDetail(Long id) {
        StudyRoomClientResult<StudyRoomRepairClientRespDTO> result = repairClient.repairDetail(id);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
