package cn.nicomoe.aimer.study.service.client.impl;

import cn.nicomoe.aimer.study.client.http.v1.StudyRoomMiscClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetGridListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetGridPriceClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseCommitClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseCommitClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseOrderListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseOverduePayClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetUserGridListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetVenueListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetWifiOpenClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomConsumeResultClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVerifyClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReceiptConsumeClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomWxPayParamClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomMiscService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自习室其他域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomMiscServiceImpl implements StudyRoomMiscService {

    private final StudyRoomMiscClient miscClient;

    @Override
    public StudyRoomCabinetLeaseOrderListClientRespDTO cabinetLeaseOrderList() {
        StudyRoomClientResult<StudyRoomCabinetLeaseOrderListClientRespDTO> result = miscClient.cabinetLeaseOrderList();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomWxPayParamClientRespDTO cabinetLeasePay(Long leaseId, Long userId) {
        StudyRoomClientResult<StudyRoomWxPayParamClientRespDTO> result = miscClient.cabinetLeasePay(leaseId, userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomCabinetLeaseCommitClientRespDTO cabinetLeaseCommit(StudyRoomCabinetLeaseCommitClientReqDTO form) {
        StudyRoomClientResult<StudyRoomCabinetLeaseCommitClientRespDTO> result = miscClient.cabinetLeaseCommit(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomCabinetLeaseOverduePayClientRespDTO cabinetLeaseOverduePay(Long leaseId, String userCode) {
        StudyRoomClientResult<StudyRoomCabinetLeaseOverduePayClientRespDTO> result = miscClient.cabinetLeaseOverduePay(leaseId, userCode);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public void cabinetLeaseAutoCheckout(Long lastLeaseId) {
        miscClient.cabinetLeaseAutoCheckout(lastLeaseId);
    }

    @Override
    public void cabinetWifiOpen(StudyRoomCabinetWifiOpenClientReqDTO form) {
        miscClient.cabinetWifiOpen(form);
    }

    @Override
    public StudyRoomCabinetVenueListClientRespDTO cabinetVenueList(Integer venueId) {
        StudyRoomClientResult<StudyRoomCabinetVenueListClientRespDTO> result = miscClient.cabinetVenueList(venueId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomCabinetGridListClientRespDTO cabinetGridList(Integer cabinetId) {
        StudyRoomClientResult<StudyRoomCabinetGridListClientRespDTO> result = miscClient.cabinetGridList(cabinetId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomCabinetUserGridListClientRespDTO cabinetUserGridList(Integer venueId) {
        StudyRoomClientResult<StudyRoomCabinetUserGridListClientRespDTO> result = miscClient.cabinetUserGridList(venueId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomCabinetGridPriceClientRespDTO cabinetGridPrice(Integer gridId) {
        StudyRoomClientResult<StudyRoomCabinetGridPriceClientRespDTO> result = miscClient.cabinetGridPrice(gridId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomConsumeResultClientRespDTO receiptConsume(StudyRoomReceiptConsumeClientReqDTO form) {
        StudyRoomClientResult<StudyRoomConsumeResultClientRespDTO> result = miscClient.receiptConsume(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomConsumeResultClientRespDTO douYinVerify(StudyRoomDouYinVerifyClientReqDTO form) {
        StudyRoomClientResult<StudyRoomConsumeResultClientRespDTO> result = miscClient.douYinVerify(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
