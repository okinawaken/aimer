package cn.nicomoe.aimer.study.service.client.impl;

import cn.nicomoe.aimer.study.client.http.v1.StudyRoomUserClient;
import cn.nicomoe.aimer.study.client.http.v3.StudyRoomUserV3Client;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAuthorizeHeadImgClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUpdateUserInfoClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserDetailClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserFeeClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSignListClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自习室用户域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomUserServiceImpl implements StudyRoomUserService {

    private final StudyRoomUserClient userClient;
    private final StudyRoomUserV3Client userV3Client;

    @Override
    public StudyRoomUserIndexClientRespDTO userIndex() {
        StudyRoomClientResult<StudyRoomUserIndexClientRespDTO> result = userClient.userIndex();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomUserDetailClientRespDTO userDetail() {
        StudyRoomClientResult<StudyRoomUserDetailClientRespDTO> result = userClient.userDetail();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomUserDetailClientRespDTO userDetail(String token) {
        StudyRoomClientResult<StudyRoomUserDetailClientRespDTO> result = userV3Client.userDetail(token);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomUserFeeClientRespDTO userFee() {
        StudyRoomClientResult<StudyRoomUserFeeClientRespDTO> result = userClient.userFee();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Integer updateUserInfo(StudyRoomUpdateUserInfoClientReqDTO form) {
        StudyRoomClientResult<Integer> result = userClient.updateUserInfo(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public void authorizeHeadImg(StudyRoomAuthorizeHeadImgClientReqDTO form) {
        userClient.authorizeHeadImg(form);
    }

    @Override
    public void cancelAuthorize() {
        userClient.cancelAuthorize();
    }

    @Override
    public StudyRoomUserSignListClientRespDTO userSignList(Long id) {
        StudyRoomClientResult<StudyRoomUserSignListClientRespDTO> result = userClient.userSignList(id);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomUserSignListClientRespDTO userSignListMore() {
        StudyRoomClientResult<StudyRoomUserSignListClientRespDTO> result = userClient.userSignListMore();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
