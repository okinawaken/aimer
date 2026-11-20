package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.AgreementDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.CountryAreaRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.CredentialListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DeregistrationRespDTO;

import java.util.List;

/** 阿里鱼协议 / 凭证 / 地区域服务，返回已解包的业务数据。 */
public interface AliyuAgreementService {

    /** 协议详情。 */
    AgreementDetailRespDTO detail();

    /** 注销协议。 */
    DeregistrationRespDTO deregistration();

    /** 协议记录列表。 */
    List<AgreementRecordListRespDTO> recordList();

    /** 协议记录详情。 */
    AgreementRecordDetailRespDTO recordDetail(String recordId);

    /** 凭证列表。 */
    List<CredentialListRespDTO> credentialList(String state, String scene, String eventIds);

    /** 国家地区（注册用）。 */
    CountryAreaRespDTO queryForUserRegister();
}
