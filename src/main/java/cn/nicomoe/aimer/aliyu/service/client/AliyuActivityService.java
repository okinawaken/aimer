package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActAssistReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActAssistRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActParticipateReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.FissionDrawActRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.InviteCodeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShareImgRespDTO;

/** 阿里鱼活动域（裂变 / 抽奖）服务，返回已解包的业务数据。 */
public interface AliyuActivityService {

    /** 裂变抽奖活动详情。 */
    FissionDrawActRespDTO fissionDrawActDetail(String actId);

    /** 获取邀请码。 */
    InviteCodeRespDTO getInviteCode(String actId);

    /** 获取分享图。 */
    ShareImgRespDTO getShareImg(String actId);

    /** 报名参与活动。 */
    void registerActParticipate(ActivityRegisterActParticipateReqDTO form);

    /** 助力（被邀请人助力）。 */
    ActivityRegisterActAssistRespDTO registerActAssist(ActivityRegisterActAssistReqDTO form);
}
