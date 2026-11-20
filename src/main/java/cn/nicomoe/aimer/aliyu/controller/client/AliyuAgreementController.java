package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.AgreementDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.CountryAreaRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.CredentialListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DeregistrationRespDTO;
import cn.nicomoe.aimer.aliyu.model.vo.AgreementDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.AgreementRecordDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.AgreementRecordListVO;
import cn.nicomoe.aimer.aliyu.model.vo.CountryAreaVO;
import cn.nicomoe.aimer.aliyu.model.vo.CredentialListVO;
import cn.nicomoe.aimer.aliyu.model.vo.DeregistrationVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuAgreementService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

import java.util.List;

/**
 * 阿里鱼协议 / 凭证 / 地区域接口。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/agreement")
public class AliyuAgreementController {

    private final AliyuAgreementService agreementService;

    /**
     * 协议详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/detail")
    public R<AgreementDetailVO> detail() {
        AgreementDetailRespDTO detailDTO = agreementService.detail();
        AgreementDetailVO detailVO = MapstructUtils.convert(detailDTO, AgreementDetailVO.class);
        return R.ok(detailVO);
    }

    /**
     * 注销协议。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/deregistration")
    public R<DeregistrationVO> deregistration() {
        DeregistrationRespDTO deregistrationDTO = agreementService.deregistration();
        DeregistrationVO deregistrationVO = MapstructUtils.convert(deregistrationDTO, DeregistrationVO.class);
        return R.ok(deregistrationVO);
    }

    /**
     * 协议记录列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/record/list")
    public R<List<AgreementRecordListVO>> recordList() {
        List<AgreementRecordListRespDTO> recordListDTO = agreementService.recordList();
        List<AgreementRecordListVO> recordListVO = MapstructUtils.convert(recordListDTO, AgreementRecordListVO.class);
        return R.ok(recordListVO);
    }

    /**
     * 协议记录详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/record/{recordId}")
    public R<AgreementRecordDetailVO> recordDetail(@PathVariable(value = "recordId") String recordId) {
        AgreementRecordDetailRespDTO recordDetailDTO = agreementService.recordDetail(recordId);
        AgreementRecordDetailVO recordDetailVO = MapstructUtils.convert(recordDetailDTO, AgreementRecordDetailVO.class);
        return R.ok(recordDetailVO);
    }

    /**
     * 凭证列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/credential/list")
    public R<List<CredentialListVO>> credentialList(
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "scene", required = false) String scene,
            @RequestParam(value = "eventIds", required = false) String eventIds) {
        List<CredentialListRespDTO> credentialListDTO = agreementService.credentialList(state, scene, eventIds);
        List<CredentialListVO> credentialListVO = MapstructUtils.convert(credentialListDTO, CredentialListVO.class);
        return R.ok(credentialListVO);
    }

    /**
     * 国家地区（注册用），{@code all=false} 时仅返回配置可选的国家 / 地区。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/country-area")
    public R<CountryAreaVO> queryForUserRegister(
            @RequestParam(value = "all", required = false) Boolean all) {
        CountryAreaRespDTO countryAreaDTO = agreementService.queryForUserRegister(all);
        CountryAreaVO countryAreaVO = MapstructUtils.convert(countryAreaDTO, CountryAreaVO.class);
        return R.ok(countryAreaVO);
    }
}
