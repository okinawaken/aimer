package cn.nicomoe.aimer.aliyu.service.client;

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

import java.util.List;

/** 阿里鱼会员 / 积分域服务，返回已解包的业务数据。 */
public interface AliyuMemberPointService {

    /** 阿里鱼会员信息。 */
    AliyuUserInfoRespDTO userInfo();

    /** 会员详情。 */
    MemberInfoRespDTO getMemberInfo();

    /** 会员首页信息（已登录时启动链路调用）。 */
    MemberHomePageRespDTO getMemberHomePageInfo();

    /** 校验会员资格。 */
    Boolean checkMember();

    /** 小鱼丸（积分）明细。 */
    PointsDetailRespDTO getPointsDetail(MemberPointPointsDetailReqDTO query);

    /** 积分规则。 */
    List<PointsRuleRespDTO> getPointsRule(String cardType);

    /** 成长值明细。 */
    GrowthValueDetailRespDTO getGrowthValueDetail(MemberPointGrowthValueDetailReqDTO query);

    /** 成长值等级信息。 */
    GrowthValueTierRespDTO getGrowthValueTierInfo();

    /** 动态会员码（到店出示）。 */
    DynamicQrCodeRespDTO getDynamicQrCode(String cardType);

    /** 修改会员资料（昵称 / 性别 / 生日）。 */
    Object modifyUser(MemberPointModifyUserReqDTO form);

    /** 注销会员。 */
    MemberPointDeregisterUserRespDTO deregisterUser(MemberPointDeregisterUserReqDTO form);

    /** 批量订阅（消息）。 */
    Boolean batchSubscribe(MemberPointBatchSubscribeReqDTO form);

    /** 批量查询订阅状态。 */
    List<MemberPointBatchQuerySubscribedRespDTO> batchQuerySubscribed(MemberPointBatchQuerySubscribedReqDTO form);

    /** 任务列表。 */
    TaskListRespDTO taskList(MemberPointTaskListReqDTO form);

    /** 任务状态检查。 */
    MemberPointTaskCheckStatusRespDTO taskCheckStatus(MemberPointTaskCheckStatusReqDTO form);

    /** 任务领取（领积分）。 */
    MemberPointTaskClaimRespDTO taskClaim(MemberPointTaskClaimReqDTO form);

    /** 任务上报（完成行为）。 */
    MemberPointTaskReportRespDTO taskReport(MemberPointTaskReportReqDTO form);
}
