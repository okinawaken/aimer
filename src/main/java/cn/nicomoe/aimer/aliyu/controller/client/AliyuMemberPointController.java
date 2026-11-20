package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DynamicQrCodeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.GrowthValueDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.GrowthValueTierRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberHomePageRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointBatchQuerySubscribedReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointBatchQuerySubscribedRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointBatchSubscribeReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointDeregisterUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointDeregisterUserRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointGrowthValueDetailReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointModifyUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointPointsDetailReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskCheckStatusReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskCheckStatusRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskClaimReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskClaimRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskReportReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskReportRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsRuleRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TaskListRespDTO;
import cn.nicomoe.aimer.aliyu.model.vo.AliyuUserInfoVO;
import cn.nicomoe.aimer.aliyu.model.vo.DynamicQrCodeVO;
import cn.nicomoe.aimer.aliyu.model.vo.GrowthValueDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.GrowthValueTierVO;
import cn.nicomoe.aimer.aliyu.model.vo.MemberHomePageVO;
import cn.nicomoe.aimer.aliyu.model.vo.MemberInfoVO;
import cn.nicomoe.aimer.aliyu.model.vo.MemberPointBatchQuerySubscribedVO;
import cn.nicomoe.aimer.aliyu.model.vo.MemberPointDeregisterUserVO;
import cn.nicomoe.aimer.aliyu.model.vo.MemberPointTaskCheckStatusVO;
import cn.nicomoe.aimer.aliyu.model.vo.MemberPointTaskClaimVO;
import cn.nicomoe.aimer.aliyu.model.vo.MemberPointTaskReportVO;
import cn.nicomoe.aimer.aliyu.model.vo.PointsDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.PointsRuleVO;
import cn.nicomoe.aimer.aliyu.model.vo.TaskListVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuMemberPointService;
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

import java.util.List;

/**
 * 阿里鱼会员 / 积分域接口。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/member-point")
public class AliyuMemberPointController {

    private final AliyuMemberPointService memberPointService;

    /**
     * 阿里鱼会员信息。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/user-info")
    public R<AliyuUserInfoVO> userInfo() {
        AliyuUserInfoRespDTO userInfoDTO = memberPointService.userInfo();
        AliyuUserInfoVO userInfoVO = MapstructUtils.convert(userInfoDTO, AliyuUserInfoVO.class);
        return R.ok(userInfoVO);
    }

    /**
     * 会员详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/member-info")
    public R<MemberInfoVO> getMemberInfo() {
        MemberInfoRespDTO memberInfoDTO = memberPointService.getMemberInfo();
        MemberInfoVO memberInfoVO = MapstructUtils.convert(memberInfoDTO, MemberInfoVO.class);
        return R.ok(memberInfoVO);
    }

    /**
     * 会员首页信息。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/member-home-page")
    public R<MemberHomePageVO> getMemberHomePageInfo() {
        MemberHomePageRespDTO memberHomePageDTO = memberPointService.getMemberHomePageInfo();
        MemberHomePageVO memberHomePageVO = MapstructUtils.convert(memberHomePageDTO, MemberHomePageVO.class);
        return R.ok(memberHomePageVO);
    }

    /**
     * 校验会员资格。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/check-member")
    public R<Boolean> checkMember() {
        return R.ok(memberPointService.checkMember());
    }

    /**
     * 小鱼丸（积分）明细。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/points-detail")
    public R<PointsDetailVO> getPointsDetail(MemberPointPointsDetailReqDTO query) {
        PointsDetailRespDTO pointsDetailDTO = memberPointService.getPointsDetail(query);
        PointsDetailVO pointsDetailVO = MapstructUtils.convert(pointsDetailDTO, PointsDetailVO.class);
        return R.ok(pointsDetailVO);
    }

    /**
     * 积分规则。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/points-rule")
    public R<List<PointsRuleVO>> getPointsRule(
            @RequestParam(value = "cardType", required = false) String cardType) {
        List<PointsRuleRespDTO> pointsRuleListDTO = memberPointService.getPointsRule(cardType);
        List<PointsRuleVO> pointsRuleListVO = MapstructUtils.convert(pointsRuleListDTO, PointsRuleVO.class);
        return R.ok(pointsRuleListVO);
    }

    /**
     * 成长值明细。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/growth-value-detail")
    public R<GrowthValueDetailVO> getGrowthValueDetail(MemberPointGrowthValueDetailReqDTO query) {
        GrowthValueDetailRespDTO growthValueDetailDTO = memberPointService.getGrowthValueDetail(query);
        GrowthValueDetailVO growthValueDetailVO = MapstructUtils.convert(growthValueDetailDTO, GrowthValueDetailVO.class);
        return R.ok(growthValueDetailVO);
    }

    /**
     * 成长值等级信息。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/growth-value-tier")
    public R<GrowthValueTierVO> getGrowthValueTierInfo() {
        GrowthValueTierRespDTO growthValueTierDTO = memberPointService.getGrowthValueTierInfo();
        GrowthValueTierVO growthValueTierVO = MapstructUtils.convert(growthValueTierDTO, GrowthValueTierVO.class);
        return R.ok(growthValueTierVO);
    }

    /**
     * 动态会员码（到店出示）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/dynamic-qr-code")
    public R<DynamicQrCodeVO> getDynamicQrCode(
            @RequestParam(value = "cardType", required = false) String cardType) {
        DynamicQrCodeRespDTO dynamicQrCodeDTO = memberPointService.getDynamicQrCode(cardType);
        DynamicQrCodeVO dynamicQrCodeVO = MapstructUtils.convert(dynamicQrCodeDTO, DynamicQrCodeVO.class);
        return R.ok(dynamicQrCodeVO);
    }

    /**
     * 修改会员资料（昵称 / 性别 / 生日）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/modify-user")
    public R<Boolean> modifyUser(@RequestBody MemberPointModifyUserReqDTO form) {
        return R.ok(memberPointService.modifyUser(form));
    }

    /**
     * 注销会员。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/deregister-user")
    public R<MemberPointDeregisterUserVO> deregisterUser(@RequestBody MemberPointDeregisterUserReqDTO form) {
        MemberPointDeregisterUserRespDTO deregisterUserDTO = memberPointService.deregisterUser(form);
        MemberPointDeregisterUserVO deregisterUserVO = MapstructUtils.convert(deregisterUserDTO, MemberPointDeregisterUserVO.class);
        return R.ok(deregisterUserVO);
    }

    /**
     * 批量订阅（消息）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/batch-subscribe")
    public R<Boolean> batchSubscribe(@RequestBody MemberPointBatchSubscribeReqDTO form) {
        return R.ok(memberPointService.batchSubscribe(form));
    }

    /**
     * 批量查询订阅状态。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/batch-query-subscribed")
    public R<List<MemberPointBatchQuerySubscribedVO>> batchQuerySubscribed(
            @RequestBody MemberPointBatchQuerySubscribedReqDTO form) {
        List<MemberPointBatchQuerySubscribedRespDTO> subscribedListDTO = memberPointService.batchQuerySubscribed(form);
        List<MemberPointBatchQuerySubscribedVO> subscribedListVO = MapstructUtils.convert(subscribedListDTO, MemberPointBatchQuerySubscribedVO.class);
        return R.ok(subscribedListVO);
    }

    /**
     * 任务列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/task/list")
    public R<TaskListVO> taskList(@RequestBody MemberPointTaskListReqDTO form) {
        TaskListRespDTO taskListDTO = memberPointService.taskList(form);
        TaskListVO taskListVO = MapstructUtils.convert(taskListDTO, TaskListVO.class);
        return R.ok(taskListVO);
    }

    /**
     * 任务状态检查。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/task/check-status")
    public R<MemberPointTaskCheckStatusVO> taskCheckStatus(
            @RequestBody MemberPointTaskCheckStatusReqDTO form) {
        MemberPointTaskCheckStatusRespDTO taskCheckStatusDTO = memberPointService.taskCheckStatus(form);
        MemberPointTaskCheckStatusVO taskCheckStatusVO = MapstructUtils.convert(taskCheckStatusDTO, MemberPointTaskCheckStatusVO.class);
        return R.ok(taskCheckStatusVO);
    }

    /**
     * 任务领取（领积分）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/task/claim")
    public R<MemberPointTaskClaimVO> taskClaim(@RequestBody MemberPointTaskClaimReqDTO form) {
        MemberPointTaskClaimRespDTO taskClaimDTO = memberPointService.taskClaim(form);
        MemberPointTaskClaimVO taskClaimVO = MapstructUtils.convert(taskClaimDTO, MemberPointTaskClaimVO.class);
        return R.ok(taskClaimVO);
    }

    /**
     * 任务上报（完成行为）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/task/report")
    public R<MemberPointTaskReportVO> taskReport(@RequestBody MemberPointTaskReportReqDTO form) {
        MemberPointTaskReportRespDTO taskReportDTO = memberPointService.taskReport(form);
        MemberPointTaskReportVO taskReportVO = MapstructUtils.convert(taskReportDTO, MemberPointTaskReportVO.class);
        return R.ok(taskReportVO);
    }
}
