package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.Map;
import cn.nicomoe.aimer.aliyu.model.dto.resp.FissionDrawActRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.InviteCodeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.ShareImgRespDTO;

/**
 * 阿里鱼活动域（裂变 / 抽奖）：{@code /aliyu/act}。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuActivityClient extends AliyuBaseClient {

    /**
     * 裂变抽奖活动详情。
     *
     * @param actId 活动 ID
     * @return 响应数据
     */
    @Get(url = "/aliyu/act/fissionDrawActDetail")
    AliyuClientResult<FissionDrawActRespDTO> fissionDrawActDetail(@Query(value = "actId") String actId);

    /**
     * 获取邀请码。
     *
     * @param actId 活动 ID
     * @return 响应数据
     */
    @Get(url = "/aliyu/act/getInviteCode")
    AliyuClientResult<InviteCodeRespDTO> getInviteCode(@Query(value = "actId") String actId);

    /**
     * 获取分享图。
     *
     * @param actId 活动 ID
     * @return 响应数据
     */
    @Get(url = "/aliyu/act/getShareImg")
    AliyuClientResult<ShareImgRespDTO> getShareImg(@Query(value = "actId") String actId);

    /**
     * 报名参与活动（入参 {@code actId}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyu/act/registerActParticipate")
    AliyuClientResult<Object> registerActParticipate(@JSONBody Map<String, Object> body);

    /**
     * 助力（被邀请人助力，入参 {@code isNewUser, userShareLinkValue}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyu/act/registerActAssist")
    AliyuClientResult<Object> registerActAssist(@JSONBody Map<String, Object> body);
}
