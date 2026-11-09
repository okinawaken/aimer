package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatConfirmClientRespDTO;
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
import cn.nicomoe.aimer.study.model.vo.StudyRoomChangeSeatConfirmVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomIndexVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomRankVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomSeatConfirmVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomSettlePackageVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomSettlementVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomSignAnotherCheckVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomSignOutVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomSignSeatListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomSignSeatMapVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserPackageConfirmVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomSignService;
import cn.nicomoe.aimer.util.MapstructUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

import java.util.List;

/**
 * 自习室签到域接口：签到、换座、签退、排行榜
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/sign")
public class StudyRoomSignController {

    private final StudyRoomSignService signService;

    /**
     * 签到首页：当前签到信息、附近门店、可选套餐
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/index")
    public R<StudyRoomIndexVO> studyIndex(StudyRoomIndexClientReqDTO form) {
        StudyRoomIndexClientRespDTO studyIndexDTO = signService.studyIndex(form);
        StudyRoomIndexVO studyIndexVO = MapstructUtils.convert(studyIndexDTO, StudyRoomIndexVO.class);
        return R.ok(studyIndexVO);
    }

    /**
     * 签到选座座位图
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/seat/list")
    public R<StudyRoomSignSeatListVO> studySeatList(@RequestBody StudyRoomSignSeatQueryClientReqDTO form) {
        StudyRoomSignSeatListClientRespDTO seatListDTO = signService.studySeatList(form);
        StudyRoomSignSeatListVO seatListVO = MapstructUtils.convert(seatListDTO, StudyRoomSignSeatListVO.class);
        return R.ok(seatListVO);
    }

    /**
     * 换座座位图
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/change/seat/list")
    public R<StudyRoomSignSeatMapVO> studyChangeSeatList(@RequestBody StudyRoomChangeSeatClientReqDTO form) {
        StudyRoomSignSeatMapClientRespDTO seatMapDTO = signService.studyChangeSeatList(form);
        StudyRoomSignSeatMapVO seatMapVO = MapstructUtils.convert(seatMapDTO, StudyRoomSignSeatMapVO.class);
        return R.ok(seatMapVO);
    }

    /**
     * 签退可选结算套餐
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/settle-package/select")
    public R<List<StudyRoomSettlePackageVO>> studySettlePackageSelect(@RequestBody StudyRoomSettlePackageClientReqDTO form) {
        List<StudyRoomSettlePackageClientRespDTO> settlePackageListDTO = signService.studySettlePackageSelect(form);
        List<StudyRoomSettlePackageVO> settlePackageListVO = MapstructUtils.convert(settlePackageListDTO, StudyRoomSettlePackageVO.class);
        return R.ok(settlePackageListVO);
    }

    /**
     * 是否可再签一个座位
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/another/check")
    public R<StudyRoomSignAnotherCheckVO> studySignAnotherCheck(@RequestParam(value = "venueId") Integer venueId) {
        StudyRoomSignAnotherCheckClientRespDTO anotherCheckDTO = signService.studySignAnotherCheck(venueId);
        StudyRoomSignAnotherCheckVO anotherCheckVO = MapstructUtils.convert(anotherCheckDTO, StudyRoomSignAnotherCheckVO.class);
        return R.ok(anotherCheckVO);
    }

    /**
     * 签退（扫码签退），返回结算结果
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/out")
    public R<StudyRoomSignOutVO> studySignOut(@RequestBody StudyRoomSignOutClientReqDTO form) {
        StudyRoomSignOutClientRespDTO signOutDTO = signService.studySignOut(form);
        StudyRoomSignOutVO signOutVO = MapstructUtils.convert(signOutDTO, StudyRoomSignOutVO.class);
        return R.ok(signOutVO);
    }

    /**
     * 学习排行榜
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/rank/index")
    public R<StudyRoomRankVO> studyRank(@RequestBody StudyRoomRankClientReqDTO form) {
        StudyRoomRankClientRespDTO rankDTO = signService.studyRank(form);
        StudyRoomRankVO rankVO = MapstructUtils.convert(rankDTO, StudyRoomRankVO.class);
        return R.ok(rankVO);
    }

    /**
     * 签到选座确认（真正入座）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/seat/confirm")
    public R<StudyRoomSeatConfirmVO> studySeatConfirm(@RequestBody StudyRoomSeatConfirmClientReqDTO form) {
        StudyRoomSeatConfirmClientRespDTO seatConfirmDTO = signService.studySeatConfirm(form);
        StudyRoomSeatConfirmVO seatConfirmVO = MapstructUtils.convert(seatConfirmDTO, StudyRoomSeatConfirmVO.class);
        return R.ok(seatConfirmVO);
    }

    /**
     * 换座确认
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/change/seat/confirm")
    public R<StudyRoomChangeSeatConfirmVO> studyChangeSeatConfirm(@RequestBody StudyRoomChangeSeatConfirmClientReqDTO form) {
        StudyRoomChangeSeatConfirmClientRespDTO changeSeatConfirmDTO = signService.studyChangeSeatConfirm(form);
        StudyRoomChangeSeatConfirmVO changeSeatConfirmVO = MapstructUtils.convert(changeSeatConfirmDTO, StudyRoomChangeSeatConfirmVO.class);
        return R.ok(changeSeatConfirmVO);
    }

    /**
     * 进入签退结算：返回结算明细 + 可选结算套餐
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/settlement")
    public R<StudyRoomSettlementVO> studySettlement(@RequestBody StudyRoomSettlementClientReqDTO form) {
        StudyRoomSettlementClientRespDTO settlementDTO = signService.studySettlement(form);
        StudyRoomSettlementVO settlementVO = MapstructUtils.convert(settlementDTO, StudyRoomSettlementVO.class);
        return R.ok(settlementVO);
    }

    /**
     * 确认签退结算套餐（选套餐扣费）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/user-package/confirm")
    public R<StudyRoomUserPackageConfirmVO> studyUserPackageConfirm(@RequestBody StudyRoomUserPackageConfirmClientReqDTO form) {
        StudyRoomUserPackageConfirmClientRespDTO userPackageConfirmDTO = signService.studyUserPackageConfirm(form);
        StudyRoomUserPackageConfirmVO userPackageConfirmVO = MapstructUtils.convert(userPackageConfirmDTO, StudyRoomUserPackageConfirmVO.class);
        return R.ok(userPackageConfirmVO);
    }

    /**
     * 座位灯控（开灯 / 关灯）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/esp-light/turn")
    public R<Void> espLightTurn(@Valid @RequestBody StudyRoomEspLightTurnClientReqDTO form) {
        signService.espLightTurn(form);
        return R.ok();
    }
}
