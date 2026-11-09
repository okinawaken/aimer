package cn.nicomoe.aimer.study.service.client.impl;

import cn.nicomoe.aimer.study.client.http.v1.StudyRoomGuardClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomGuardListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomGuardOpenClientReqDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomGuardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自习室门禁域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomGuardServiceImpl implements StudyRoomGuardService {

    private final StudyRoomGuardClient guardClient;

    @Override
    public StudyRoomGuardListClientRespDTO guardSelect(Integer venueId) {
        StudyRoomClientResult<StudyRoomGuardListClientRespDTO> result = guardClient.guardSelect(venueId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public void guardOpen(StudyRoomGuardOpenClientReqDTO form) {
        guardClient.guardOpen(form);
    }
}
