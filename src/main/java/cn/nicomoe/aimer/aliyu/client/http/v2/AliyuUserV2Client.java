package cn.nicomoe.aimer.aliyu.client.http.v2;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.BearerListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DisListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.LoginUserRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserAddBearerReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserAddBearerRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserAddDisReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserContactRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserGetDisInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserGetDisReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserLoginOffReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserModifyDisReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserModifyUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserRemoveBearerReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserRemoveDisReqDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Var;

/** 用户域（显式用户上下文）。资料、持票人、联系人、退出登录。 */
public interface AliyuUserV2Client extends AliyuBaseClient {

    /** 获取登录用户信息。 */
    @Get(url = "/user/loginUser")
    AliyuClientResult<LoginUserRespDTO> loginUser(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 用户信息。 */
    @Get(url = "/user/userInfo")
    AliyuClientResult<UserInfoRespDTO> userInfo(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 用户联系方式。 */
    @Get(url = "/user/userContact")
    AliyuClientResult<UserContactRespDTO> userContact(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 获取持票人列表。 参数 page：页码参数 pageSize：每页数量参数 eventIds：场次 ID（逗号分隔） 参数 exitTicketCert：退票凭证（逗号分隔） */
    @Get(url = "/user/getBearers")
    AliyuClientResult<BearerListRespDTO> getBearers(@Query(value = "page") Integer page,
                                                    @Query(value = "pageSize") Integer pageSize,
                                                    @Query(value = "eventIds") String eventIds,
                                                    @Query(value = "exitTicketCert") String exitTicketCert,
                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 获取联系人 / 收件人列表。 参数 query：分页（小程序传 page=1、pageSize=20） 参数 userId：系统用户 ID */
    @Get(url = "/user/getDis")
    AliyuClientResult<DisListRespDTO> getDis(@Query UserGetDisReqDTO query,
                                             @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 联系人详情。 参数 disId：联系人 ID */
    @Get(url = "/user/getDisInfo")
    AliyuClientResult<UserGetDisInfoRespDTO> getDisInfo(@Query(value = "disId") String disId,
                                                        @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 修改用户信息。 */
    @Post(url = "/user/modifyUser")
    AliyuClientResult<Object> modifyUser(@JSONBody UserModifyUserReqDTO body,
                                         @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 修改联系人。 */
    @Post(url = "/user/modifyDis")
    AliyuClientResult<Object> modifyDis(@JSONBody UserModifyDisReqDTO body,
                                        @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 新增持票人。 */
    @Post(url = "/user/addBearer")
    AliyuClientResult<UserAddBearerRespDTO> addBearer(@JSONBody UserAddBearerReqDTO body,
                                                      @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 删除持票人。 */
    @Post(url = "/user/removeBearer")
    AliyuClientResult<Object> removeBearer(@JSONBody UserRemoveBearerReqDTO body,
                                           @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 新增联系人。 */
    @Post(url = "/user/addDis")
    AliyuClientResult<Object> addDis(@JSONBody UserAddDisReqDTO body,
                                     @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 删除联系人。 */
    @Post(url = "/user/removeDis")
    AliyuClientResult<Object> removeDis(@JSONBody UserRemoveDisReqDTO body,
                                        @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 退出登录。 */
    @Post(url = "/user/loginOff")
    AliyuClientResult<Object> loginOff(@JSONBody UserLoginOffReqDTO body,
                                       @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);
}
