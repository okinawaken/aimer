package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
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
import com.dtflys.forest.annotation.FormBody;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

/** 阿里鱼会员 / 积分域。会员信息、小鱼丸（积分）明细、成长值、动态码、 任务体系、消息订阅。 */
public interface AliyuMemberPointClient extends AliyuBaseClient {

    /** 阿里鱼会员信息。 */
    @Get(url = "/aliyupoint/userInfo")
    AliyuClientResult<AliyuUserInfoRespDTO> userInfo();

    /** 会员详情。 */
    @Get(url = "/aliyupoint/getMemberInfo")
    AliyuClientResult<MemberInfoRespDTO> getMemberInfo();

    /** 会员首页信息（已登录时启动链路调用）。 */
    @Get(url = "/aliyupoint/getMemberHomePageInfo")
    AliyuClientResult<MemberHomePageRespDTO> getMemberHomePageInfo();

    /** 校验会员资格。 */
    @Get(url = "/aliyupoint/checkMember")
    AliyuClientResult<Boolean> checkMember();

    /** 小鱼丸（积分）明细。 参数 query：会员卡类型与分页 */
    @Get(url = "/aliyupoint/getPointsDetail")
    AliyuClientResult<PointsDetailRespDTO> getPointsDetail(@Query MemberPointPointsDetailReqDTO query);

    /** 积分规则。 */
    @Get(url = "/aliyupoint/getPointsRule")
    AliyuClientResult<java.util.List<PointsRuleRespDTO>> getPointsRule(@Query(value = "cardType") String cardType);

    /** 成长值明细。 参数 query：分页 */
    @Get(url = "/aliyupoint/getGrowthValueDetail")
    AliyuClientResult<GrowthValueDetailRespDTO> getGrowthValueDetail(@Query MemberPointGrowthValueDetailReqDTO query);

    /** 成长值等级信息。 */
    @Get(url = "/aliyupoint/getGrowthValueTierInfo")
    AliyuClientResult<GrowthValueTierRespDTO> getGrowthValueTierInfo();

    /** 动态会员码（到店出示）。 */
    @Get(url = "/aliyupoint/getDynamicQrCode")
    AliyuClientResult<DynamicQrCodeRespDTO> getDynamicQrCode(@Query(value = "cardType") String cardType);

    /** 修改会员资料（昵称 / 性别 / 生日）。 */
    @Post(url = "/aliyupoint/modifyUser")
    AliyuClientResult<Object> modifyUser(@JSONBody MemberPointModifyUserReqDTO body);

    /** 注销会员。 */
    @Post(url = "/aliyupoint/deregisterUser")
    AliyuClientResult<MemberPointDeregisterUserRespDTO> deregisterUser(@JSONBody MemberPointDeregisterUserReqDTO body);

    /** 批量订阅（消息）。成功时响应体 {@code data} 为 {@code true}，失败由 {@code code/msg} 表达。 */
    @Post(url = "/aliyupoint/batchSubscribe")
    AliyuClientResult<Boolean> batchSubscribe(@JSONBody MemberPointBatchSubscribeReqDTO body);

    /** 批量查询订阅状态。 */
    @Post(url = "/aliyupoint/batchQuerySubscribed")
    AliyuClientResult<java.util.List<MemberPointBatchQuerySubscribedRespDTO>> batchQuerySubscribed(@JSONBody MemberPointBatchQuerySubscribedReqDTO body);

    /** 任务列表。 */
    @Post(url = "/aliyupoint/task/list")
    AliyuClientResult<TaskListRespDTO> taskList(@JSONBody MemberPointTaskListReqDTO body);

    /** 任务状态检查。 */
    @Post(url = "/aliyupoint/task/checkStatus")
    AliyuClientResult<MemberPointTaskCheckStatusRespDTO> taskCheckStatus(@JSONBody MemberPointTaskCheckStatusReqDTO body);

    /** 任务领取（领积分）。响应体顶层带 {@code success/code/msg/data}， 失败码（如 {@code TASK_NOT_FOUND}）需由调用方按 {@code code} 处理，故直接返回响应体。 */
    @Post(url = "/aliyupoint/task/claim")
    MemberPointTaskClaimRespDTO taskClaim(@JSONBody MemberPointTaskClaimReqDTO body);

    /** 任务上报（完成行为）。响应体顶层带 {@code success/data}，故直接返回响应体。 小程序以 {@code application/x-www-form-urlencoded} 提交，需显式覆盖基础客户端的 JSON 类型。 */
    @Post(url = "/aliyupoint/task/report", contentType = "application/x-www-form-urlencoded")
    MemberPointTaskReportRespDTO taskReport(@FormBody MemberPointTaskReportReqDTO body);
}
