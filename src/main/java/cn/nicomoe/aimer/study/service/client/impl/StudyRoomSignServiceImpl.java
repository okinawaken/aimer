package cn.nicomoe.aimer.study.service.client.impl;

import cn.nicomoe.aimer.study.client.http.v1.StudyRoomSignClient;
import cn.nicomoe.aimer.study.client.http.v2.StudyRoomSignV2Client;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatConfirmClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomEspLightTurnClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRankClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRankClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatConfirmClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlePackageClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlePackageClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlementClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlementClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignAnotherCheckClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignOutClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignOutClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatMapClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatQueryClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageConfirmClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 自习室签到域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomSignServiceImpl implements StudyRoomSignService {

    private final StudyRoomSignClient signClient;
    private final StudyRoomSignV2Client signV2Client;

    @Override
    public StudyRoomIndexClientRespDTO studyIndex(StudyRoomIndexClientReqDTO form) {
        StudyRoomClientResult<StudyRoomIndexClientRespDTO> result = signClient.studyIndex(form.getCurrentLng(), form.getCurrentLat(), form.getVenueId(), form.getBssid(), form.getSsid());
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomIndexClientRespDTO studyIndex(StudyRoomIndexClientReqDTO form, Long userId) {
        StudyRoomClientResult<StudyRoomIndexClientRespDTO> result = signV2Client.studyIndex(form.getCurrentLng(), form.getCurrentLat(), form.getVenueId(), form.getBssid(), form.getSsid(), userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSignSeatListClientRespDTO studySeatList(StudyRoomSignSeatQueryClientReqDTO form) {
        StudyRoomClientResult<StudyRoomSignSeatListClientRespDTO> result = signClient.studySeatList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSignSeatListClientRespDTO studySeatList(
            StudyRoomSignSeatQueryClientReqDTO form, Long userId) {
        StudyRoomClientResult<StudyRoomSignSeatListClientRespDTO> result = signV2Client.studySeatList(form, userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSignSeatMapClientRespDTO studyChangeSeatList(StudyRoomChangeSeatClientReqDTO form) {
        StudyRoomClientResult<StudyRoomSignSeatMapClientRespDTO> result = signClient.studyChangeSeatList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<StudyRoomSettlePackageClientRespDTO> studySettlePackageSelect(
            StudyRoomSettlePackageClientReqDTO form) {
        StudyRoomClientResult<List<StudyRoomSettlePackageClientRespDTO>> result = signClient.studySettlePackageSelect(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<StudyRoomSettlePackageClientRespDTO> studySettlePackageSelect(
            StudyRoomSettlePackageClientReqDTO form, Long userId) {
        StudyRoomClientResult<List<StudyRoomSettlePackageClientRespDTO>> result = signV2Client.studySettlePackageSelect(form, userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSignAnotherCheckClientRespDTO studySignAnotherCheck(Integer venueId) {
        StudyRoomClientResult<StudyRoomSignAnotherCheckClientRespDTO> result = signClient.studySignAnotherCheck(venueId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSignOutClientRespDTO studySignOut(StudyRoomSignOutClientReqDTO form) {
        StudyRoomClientResult<StudyRoomSignOutClientRespDTO> result = signClient.studySignOut(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSignOutClientRespDTO studySignOut(StudyRoomSignOutClientReqDTO form, Long userId) {
        StudyRoomClientResult<StudyRoomSignOutClientRespDTO> result = signV2Client.studySignOut(form, userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomRankClientRespDTO studyRank(StudyRoomRankClientReqDTO form) {
        StudyRoomClientResult<StudyRoomRankClientRespDTO> result = signClient.studyRank(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSeatConfirmClientRespDTO studySeatConfirm(StudyRoomSeatConfirmClientReqDTO form) {
        StudyRoomClientResult<StudyRoomSeatConfirmClientRespDTO> result = signClient.studySeatConfirm(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomChangeSeatConfirmClientRespDTO studyChangeSeatConfirm(StudyRoomChangeSeatConfirmClientReqDTO form) {
        StudyRoomClientResult<StudyRoomChangeSeatConfirmClientRespDTO> result = signClient.studyChangeSeatConfirm(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomSettlementClientRespDTO studySettlement(StudyRoomSettlementClientReqDTO form) {
        StudyRoomClientResult<StudyRoomSettlementClientRespDTO> result = signClient.studySettlement(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomUserPackageConfirmClientRespDTO studyUserPackageConfirm(StudyRoomUserPackageConfirmClientReqDTO form) {
        StudyRoomClientResult<StudyRoomUserPackageConfirmClientRespDTO> result = signClient.studyUserPackageConfirm(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public void espLightTurn(StudyRoomEspLightTurnClientReqDTO form) {
        signClient.espLightTurn(form);
    }
}
