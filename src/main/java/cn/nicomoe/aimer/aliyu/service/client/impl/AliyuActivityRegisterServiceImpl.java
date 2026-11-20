package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuActivityRegisterClient;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityDrawListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.DrawListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.EventRegisterEventRegisterReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.EventRegisterUpdateSaleRemindReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegisterActDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegisterActListRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuActivityRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/** 阿里鱼活动报名 / 抽奖域服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuActivityRegisterServiceImpl implements AliyuActivityRegisterService {

    private final AliyuActivityRegisterClient activityRegisterClient;

    @Override
    public RegisterActDetailRespDTO registerActDetail(String actId) {
        AliyuClientResult<RegisterActDetailRespDTO> result = activityRegisterClient.registerActDetail(actId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public RegisterActListRespDTO registerActList(ActivityRegisterActListReqDTO form) {
        AliyuClientResult<RegisterActListRespDTO> result = activityRegisterClient.registerActList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean registerUser(ActivityRegisterUserReqDTO form) {
        AliyuClientResult<Boolean> result = activityRegisterClient.registerUser(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public DrawListRespDTO drawList(ActivityDrawListReqDTO form) {
        AliyuClientResult<DrawListRespDTO> result = activityRegisterClient.drawList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object eventRegister(EventRegisterEventRegisterReqDTO form) {
        AliyuClientResult<Object> result = activityRegisterClient.eventRegister(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object updateSaleRemind(EventRegisterUpdateSaleRemindReqDTO form) {
        AliyuClientResult<Object> result = activityRegisterClient.updateSaleRemind(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
