package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
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

/** 用户域。 */
public interface AliyuUserClient extends AliyuBaseClient {

    /** 获取登录用户信息。 */
    @Get(url = "/user/loginUser")
    AliyuClientResult<LoginUserRespDTO> loginUser();

    /** 用户信息。 */
    @Get(url = "/user/userInfo")
    AliyuClientResult<UserInfoRespDTO> userInfo();

    /** 用户联系方式。 */
    @Get(url = "/user/userContact")
    AliyuClientResult<UserContactRespDTO> userContact();

    /** 获取持票人列表。 */
    @Get(url = "/user/getBearers")
    AliyuClientResult<BearerListRespDTO> getBearers(@Query(value = "page") Integer page,
                                                    @Query(value = "pageSize") Integer pageSize,
                                                    @Query(value = "eventIds") String eventIds,
                                                    @Query(value = "exitTicketCert") String exitTicketCert);

    /** 获取联系人 / 收件人列表。 */
    @Get(url = "/user/getDis")
    AliyuClientResult<DisListRespDTO> getDis(@Query UserGetDisReqDTO query);

    /** 联系人详情。 */
    @Get(url = "/user/getDisInfo")
    AliyuClientResult<UserGetDisInfoRespDTO> getDisInfo(@Query(value = "disId") String disId);

    /** 修改用户信息。 */
    @Post(url = "/user/modifyUser")
    AliyuClientResult<Object> modifyUser(@JSONBody UserModifyUserReqDTO body);

    /** 修改联系人。 */
    @Post(url = "/user/modifyDis")
    AliyuClientResult<Object> modifyDis(@JSONBody UserModifyDisReqDTO body);

    /** 新增持票人。 */
    @Post(url = "/user/addBearer")
    AliyuClientResult<UserAddBearerRespDTO> addBearer(@JSONBody UserAddBearerReqDTO body);

    /** 删除持票人。 */
    @Post(url = "/user/removeBearer")
    AliyuClientResult<Object> removeBearer(@JSONBody UserRemoveBearerReqDTO body);

    /** 新增联系人。 */
    @Post(url = "/user/addDis")
    AliyuClientResult<Object> addDis(@JSONBody UserAddDisReqDTO body);

    /** 删除联系人。 */
    @Post(url = "/user/removeDis")
    AliyuClientResult<Object> removeDis(@JSONBody UserRemoveDisReqDTO body);

    /** 退出登录。 */
    @Post(url = "/user/loginOff")
    AliyuClientResult<Object> loginOff(@JSONBody UserLoginOffReqDTO body);
}
