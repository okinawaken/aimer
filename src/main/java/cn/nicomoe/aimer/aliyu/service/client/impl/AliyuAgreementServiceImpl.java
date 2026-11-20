package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuAgreementClient;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.CountryAreaRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.CredentialListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DeregistrationRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuAgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 阿里鱼协议 / 凭证 / 地区域服务实现。
 */
@Service
@RequiredArgsConstructor
public class AliyuAgreementServiceImpl implements AliyuAgreementService {

    private final AliyuAgreementClient agreementClient;

    @Override
    public AgreementDetailRespDTO detail() {
        AliyuClientResult<AgreementDetailRespDTO> result = agreementClient.detail();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public DeregistrationRespDTO deregistration() {
        AliyuClientResult<DeregistrationRespDTO> result = agreementClient.deregistration();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<AgreementRecordListRespDTO> recordList() {
        AliyuClientResult<List<AgreementRecordListRespDTO>> result = agreementClient.recordList();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public AgreementRecordDetailRespDTO recordDetail(String recordId) {
        AliyuClientResult<AgreementRecordDetailRespDTO> result = agreementClient.recordDetail(recordId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<CredentialListRespDTO> credentialList(String state, String scene, String eventIds) {
        AliyuClientResult<List<CredentialListRespDTO>> result = agreementClient.credentialList(state, scene, eventIds);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public CountryAreaRespDTO queryForUserRegister(Boolean all) {
        AliyuClientResult<CountryAreaRespDTO> result = agreementClient.queryForUserRegister(all);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
