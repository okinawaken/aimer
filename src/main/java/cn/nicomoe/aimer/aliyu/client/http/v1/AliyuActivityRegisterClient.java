package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityDrawListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.DrawListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.EventRegisterEventRegisterReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.EventRegisterUpdateSaleRemindReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegisterActDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegisterActListRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

/** 活动报名 / 抽奖域。 */
public interface AliyuActivityRegisterClient extends AliyuBaseClient {

    /** 报名活动详情。 */
    @Get(url = "/act/registerActDetail")
    AliyuClientResult<RegisterActDetailRespDTO> registerActDetail(@Query(value = "actId") String actId);

    /** 报名活动列表。 */
    @Post(url = "/act/registerActList")
    AliyuClientResult<RegisterActListRespDTO> registerActList(@JSONBody ActivityRegisterActListReqDTO body);

    /** 活动报名。 */
    @Post(url = "/act/registerUser")
    AliyuClientResult<Boolean> registerUser(@JSONBody ActivityRegisterUserReqDTO body);

    /** 抽奖列表。 */
    @Post(url = "/act/drawList")
    AliyuClientResult<DrawListRespDTO> drawList(@JSONBody ActivityDrawListReqDTO body);

    /** 活动注册（开售提醒登记）。 */
    @Post(url = "/event/register/eventRegister")
    AliyuClientResult<Object> eventRegister(@JSONBody EventRegisterEventRegisterReqDTO body);

    /** 更新开售提醒。 */
    @Post(url = "/event/register/updateSaleRemind")
    AliyuClientResult<Object> updateSaleRemind(@JSONBody EventRegisterUpdateSaleRemindReqDTO body);
}
