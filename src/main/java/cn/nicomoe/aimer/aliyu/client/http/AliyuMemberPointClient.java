package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.Map;
import cn.nicomoe.aimer.aliyu.model.dto.resp.AliyuUserInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.DynamicQrCodeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.GrowthValueDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.GrowthValueTierRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.MemberHomePageRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.MemberInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.PointsDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.PointsRuleRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.TaskListRespDTO;

/**
 * 阿里鱼会员 / 积分域：{@code /aliyupoint}。会员信息、小鱼丸（积分）明细、成长值、动态码、任务体系、消息订阅。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuMemberPointClient extends AliyuBaseClient {

    /**
     * 阿里鱼会员信息。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyupoint/userInfo")
    AliyuClientResult<AliyuUserInfoRespDTO> userInfo();

    /**
     * 会员详情。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyupoint/getMemberInfo")
    AliyuClientResult<MemberInfoRespDTO> getMemberInfo();

    /**
     * 会员首页信息（已登录时启动链路调用）。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyupoint/getMemberHomePageInfo")
    AliyuClientResult<MemberHomePageRespDTO> getMemberHomePageInfo();

    /**
     * 校验会员资格。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyupoint/checkMember")
    AliyuClientResult<Boolean> checkMember();

    /**
     * 小鱼丸（积分）明细。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyupoint/getPointsDetail")
    AliyuClientResult<PointsDetailRespDTO> getPointsDetail();

    /**
     * 积分规则。
     *
     * @param cardType 卡类型
     * @return 响应数据
     */
    @Get(url = "/aliyupoint/getPointsRule")
    AliyuClientResult<java.util.List<PointsRuleRespDTO>> getPointsRule(@Query(value = "cardType") String cardType);

    /**
     * 成长值明细。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyupoint/getGrowthValueDetail")
    AliyuClientResult<GrowthValueDetailRespDTO> getGrowthValueDetail();

    /**
     * 成长值等级信息。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyupoint/getGrowthValueTierInfo")
    AliyuClientResult<GrowthValueTierRespDTO> getGrowthValueTierInfo();

    /**
     * 动态会员码（到店出示）。
     *
     * @param cardType 卡类型
     * @return 响应数据
     */
    @Get(url = "/aliyupoint/getDynamicQrCode")
    AliyuClientResult<DynamicQrCodeRespDTO> getDynamicQrCode(@Query(value = "cardType") String cardType);

    /**
     * 修改会员资料。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyupoint/modifyUser")
    AliyuClientResult<Object> modifyUser(@JSONBody Map<String, Object> body);

    /**
     * 注销会员（入参 {@code msgCode, mobile, countryAreaCode}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyupoint/deregisterUser")
    AliyuClientResult<Object> deregisterUser(@JSONBody Map<String, Object> body);

    /**
     * 批量订阅（消息）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyupoint/batchSubscribe")
    AliyuClientResult<Object> batchSubscribe(@JSONBody Map<String, Object> body);

    /**
     * 批量查询订阅状态（入参 {@code items}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyupoint/batchQuerySubscribed")
    AliyuClientResult<Object> batchQuerySubscribed(@JSONBody Map<String, Object> body);

    /**
     * 任务列表（入参 {@code page, pageSize}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyupoint/task/list")
    AliyuClientResult<TaskListRespDTO> taskList(@JSONBody Map<String, Object> body);

    /**
     * 任务状态检查（入参 {@code taskId}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyupoint/task/checkStatus")
    AliyuClientResult<Object> taskCheckStatus(@JSONBody Map<String, Object> body);

    /**
     * 任务领取（领积分，入参 {@code taskId}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyupoint/task/claim")
    AliyuClientResult<Object> taskClaim(@JSONBody Map<String, Object> body);

    /**
     * 任务上报（完成行为）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyupoint/task/report")
    AliyuClientResult<Object> taskReport(@JSONBody Map<String, Object> body);
}
