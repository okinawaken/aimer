package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.ActivityDrawListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DrawListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.EventRegisterEventRegisterReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.EventRegisterUpdateSaleRemindReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegisterActDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegisterActListRespDTO;

/** 阿里鱼活动报名 / 抽奖域服务，返回已解包的业务数据。 */
public interface AliyuActivityRegisterService {

    /** 报名活动详情。 */
    RegisterActDetailRespDTO registerActDetail(String actId);

    /** 报名活动列表。 */
    RegisterActListRespDTO registerActList(ActivityRegisterActListReqDTO form);

    /** 活动报名。 */
    Boolean registerUser(ActivityRegisterUserReqDTO form);

    /** 抽奖列表。 */
    DrawListRespDTO drawList(ActivityDrawListReqDTO form);

    /** 活动注册（开售提醒登记）。 */
    Object eventRegister(EventRegisterEventRegisterReqDTO form);

    /** 更新开售提醒。 */
    Object updateSaleRemind(EventRegisterUpdateSaleRemindReqDTO form);
}
