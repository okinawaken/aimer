package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActAssistReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActAssistRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActParticipateReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.FissionDrawActRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.InviteCodeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShareImgRespDTO;
import cn.nicomoe.aimer.aliyu.model.vo.ActivityRegisterActAssistVO;
import cn.nicomoe.aimer.aliyu.model.vo.FissionDrawActVO;
import cn.nicomoe.aimer.aliyu.model.vo.InviteCodeVO;
import cn.nicomoe.aimer.aliyu.model.vo.ShareImgVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuActivityService;
import cn.nicomoe.aimer.util.MapstructUtils;
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

/**
 * 阿里鱼活动域接口：裂变抽奖、邀请码、分享图、报名参与与助力。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/activity")
public class AliyuActivityController {

    private final AliyuActivityService activityService;

    /**
     * 裂变抽奖活动详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/fission-draw-act-detail")
    public R<FissionDrawActVO> fissionDrawActDetail(@RequestParam(value = "actId") String actId) {
        FissionDrawActRespDTO detailDTO = activityService.fissionDrawActDetail(actId);
        FissionDrawActVO detailVO = MapstructUtils.convert(detailDTO, FissionDrawActVO.class);
        return R.ok(detailVO);
    }

    /**
     * 获取邀请码。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/invite-code")
    public R<InviteCodeVO> getInviteCode(@RequestParam(value = "actId") String actId) {
        InviteCodeRespDTO inviteCodeDTO = activityService.getInviteCode(actId);
        InviteCodeVO inviteCodeVO = MapstructUtils.convert(inviteCodeDTO, InviteCodeVO.class);
        return R.ok(inviteCodeVO);
    }

    /**
     * 获取分享图。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/share-img")
    public R<ShareImgVO> getShareImg(@RequestParam(value = "actId") String actId) {
        ShareImgRespDTO shareImgDTO = activityService.getShareImg(actId);
        ShareImgVO shareImgVO = MapstructUtils.convert(shareImgDTO, ShareImgVO.class);
        return R.ok(shareImgVO);
    }

    /**
     * 报名参与活动。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/participate")
    public R<Void> registerActParticipate(@RequestBody ActivityRegisterActParticipateReqDTO form) {
        activityService.registerActParticipate(form);
        return R.ok();
    }

    /**
     * 助力（被邀请人助力）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/assist")
    public R<ActivityRegisterActAssistVO> registerActAssist(@RequestBody ActivityRegisterActAssistReqDTO form) {
        ActivityRegisterActAssistRespDTO assistDTO = activityService.registerActAssist(form);
        ActivityRegisterActAssistVO assistVO = MapstructUtils.convert(assistDTO, ActivityRegisterActAssistVO.class);
        return R.ok(assistVO);
    }
}
