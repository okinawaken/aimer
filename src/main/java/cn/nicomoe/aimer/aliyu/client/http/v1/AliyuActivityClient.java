package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActAssistReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActAssistRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActParticipateReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.FissionDrawActRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.InviteCodeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShareImgRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

/** 阿里鱼活动域（裂变 / 抽奖）。 */
public interface AliyuActivityClient extends AliyuBaseClient {

    /** 裂变抽奖活动详情。 */
    @Get(url = "/aliyu/act/fissionDrawActDetail")
    AliyuClientResult<FissionDrawActRespDTO> fissionDrawActDetail(@Query(value = "actId") String actId);

    /** 获取邀请码。 */
    @Get(url = "/aliyu/act/getInviteCode")
    AliyuClientResult<InviteCodeRespDTO> getInviteCode(@Query(value = "actId") String actId);

    /** 获取分享图。 */
    @Get(url = "/aliyu/act/getShareImg")
    AliyuClientResult<ShareImgRespDTO> getShareImg(@Query(value = "actId") String actId);

    /** 报名参与活动。 */
    @Post(url = "/aliyu/act/registerActParticipate")
    AliyuClientResult<Void> registerActParticipate(@JSONBody ActivityRegisterActParticipateReqDTO body);

    /** 助力（被邀请人助力）。 */
    @Post(url = "/aliyu/act/registerActAssist")
    AliyuClientResult<ActivityRegisterActAssistRespDTO> registerActAssist(@JSONBody ActivityRegisterActAssistReqDTO body);
}
