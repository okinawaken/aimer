package cn.nicomoe.aimer.aliyu.client.http.v2;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
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
import com.dtflys.forest.annotation.Var;

/** 活动报名 / 抽奖域（显式用户上下文）。 */
public interface AliyuActivityRegisterV2Client extends AliyuBaseClient {

    /** 报名活动详情。 */
    @Get(url = "/act/registerActDetail")
    AliyuClientResult<RegisterActDetailRespDTO> registerActDetail(@Query(value = "actId") String actId,
                                                                  @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 报名活动列表。 */
    @Post(url = "/act/registerActList")
    AliyuClientResult<RegisterActListRespDTO> registerActList(@JSONBody ActivityRegisterActListReqDTO body,
                                                              @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 活动报名。 */
    @Post(url = "/act/registerUser")
    AliyuClientResult<Boolean> registerUser(@JSONBody ActivityRegisterUserReqDTO body,
                                            @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 抽奖列表。 */
    @Post(url = "/act/drawList")
    AliyuClientResult<DrawListRespDTO> drawList(@JSONBody ActivityDrawListReqDTO body,
                                                @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 活动注册（开售提醒登记）。 */
    @Post(url = "/event/register/eventRegister")
    AliyuClientResult<Object> eventRegister(@JSONBody EventRegisterEventRegisterReqDTO body,
                                            @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 更新开售提醒。 */
    @Post(url = "/event/register/updateSaleRemind")
    AliyuClientResult<Object> updateSaleRemind(@JSONBody EventRegisterUpdateSaleRemindReqDTO body,
                                               @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);
}
