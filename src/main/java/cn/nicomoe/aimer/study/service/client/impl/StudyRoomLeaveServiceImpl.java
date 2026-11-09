package cn.nicomoe.aimer.study.service.client.impl;

import cn.nicomoe.aimer.study.client.http.v1.StudyRoomLeaveClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveCancelClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveEnterClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自习室请假域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomLeaveServiceImpl implements StudyRoomLeaveService {

    private final StudyRoomLeaveClient leaveClient;

    @Override
    public StudyRoomUserLeaveEnterClientRespDTO userLeaveEnter(Long userPackageId) {
        StudyRoomClientResult<StudyRoomUserLeaveEnterClientRespDTO> result = leaveClient.userLeaveEnter(userPackageId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Integer userLeaveConfirm(StudyRoomUserLeaveConfirmClientReqDTO form) {
        StudyRoomClientResult<Integer> result = leaveClient.userLeaveConfirm(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Integer userLeaveCancel(StudyRoomUserLeaveCancelClientReqDTO form) {
        StudyRoomClientResult<Integer> result = leaveClient.userLeaveCancel(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
