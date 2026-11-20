package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuActivityClient;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActAssistReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActAssistRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActParticipateReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.FissionDrawActRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.InviteCodeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShareImgRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 阿里鱼活动域服务实现。
 */
@Service
@RequiredArgsConstructor
public class AliyuActivityServiceImpl implements AliyuActivityService {

    private final AliyuActivityClient activityClient;

    @Override
    public FissionDrawActRespDTO fissionDrawActDetail(String actId) {
        AliyuClientResult<FissionDrawActRespDTO> result = activityClient.fissionDrawActDetail(actId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public InviteCodeRespDTO getInviteCode(String actId) {
        AliyuClientResult<InviteCodeRespDTO> result = activityClient.getInviteCode(actId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ShareImgRespDTO getShareImg(String actId) {
        AliyuClientResult<ShareImgRespDTO> result = activityClient.getShareImg(actId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public void registerActParticipate(ActivityRegisterActParticipateReqDTO form) {
        activityClient.registerActParticipate(form);
    }

    @Override
    public ActivityRegisterActAssistRespDTO registerActAssist(ActivityRegisterActAssistReqDTO form) {
        AliyuClientResult<ActivityRegisterActAssistRespDTO> result = activityClient.registerActAssist(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
