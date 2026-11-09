package cn.nicomoe.aimer.study.service.client.impl;

import cn.nicomoe.aimer.study.client.http.v1.StudyRoomPackageClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyOrderClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyPackageConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyPackageListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyPayOrderClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageDetailClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageLabelClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageListClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageOptClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserOrderListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomWxPayParamClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomWxPayQueryClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自习室套餐域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomPackageServiceImpl implements StudyRoomPackageService {

    private final StudyRoomPackageClient packageClient;

    @Override
    public StudyRoomBuyPackageListClientRespDTO buyPackageList() {
        StudyRoomClientResult<StudyRoomBuyPackageListClientRespDTO> result = packageClient.buyPackageList();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomBuyV2PackageListClientRespDTO buyV2PackageList(StudyRoomBuyV2PackageListClientReqDTO form) {
        StudyRoomClientResult<StudyRoomBuyV2PackageListClientRespDTO> result = packageClient.buyV2PackageList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomBuyV2PackageOptClientRespDTO buyV2PackageOptList(Double currentLng, Double currentLat) {
        StudyRoomClientResult<StudyRoomBuyV2PackageOptClientRespDTO> result = packageClient.buyV2PackageOptList(currentLng, currentLat);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomBuyV2PackageLabelClientRespDTO buyV2PackageLabelList(Integer venueId) {
        StudyRoomClientResult<StudyRoomBuyV2PackageLabelClientRespDTO> result = packageClient.buyV2PackageLabelList(venueId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomBuyV2PackageDetailClientRespDTO buyV2PackageDetail(Long packageId) {
        StudyRoomClientResult<StudyRoomBuyV2PackageDetailClientRespDTO> result = packageClient.buyV2PackageDetail(packageId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomBuyOrderClientRespDTO buyPackageConfirm(StudyRoomBuyPackageConfirmClientReqDTO form) {
        StudyRoomClientResult<StudyRoomBuyOrderClientRespDTO> result = packageClient.buyPackageConfirm(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomBuyOrderClientRespDTO buyV2PackageConfirm(StudyRoomBuyV2PackageConfirmClientReqDTO form) {
        StudyRoomClientResult<StudyRoomBuyOrderClientRespDTO> result = packageClient.buyV2PackageConfirm(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomWxPayParamClientRespDTO buyPackagePayOrder(StudyRoomBuyPayOrderClientReqDTO form) {
        StudyRoomClientResult<StudyRoomWxPayParamClientRespDTO> result = packageClient.buyPackagePayOrder(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomUserOrderListClientRespDTO userOrderList() {
        StudyRoomClientResult<StudyRoomUserOrderListClientRespDTO> result = packageClient.userOrderList();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomUserPackageListClientRespDTO userPackageList() {
        StudyRoomClientResult<StudyRoomUserPackageListClientRespDTO> result = packageClient.userPackageList();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomWxPayQueryClientRespDTO wxPayQueryStatus(Long payId) {
        StudyRoomClientResult<StudyRoomWxPayQueryClientRespDTO> result = packageClient.wxPayQueryStatus(payId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
