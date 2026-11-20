package cn.nicomoe.aimer.aliyu.service.client;

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

/** 阿里鱼用户域服务，返回已解包的业务数据。 */
public interface AliyuUserService {

    /** 获取登录用户信息。 */
    LoginUserRespDTO loginUser();

    /** 用显式凭证获取登录用户信息，供绑定校验使用。 参数 cookie：持久会话 Cookie返回：登录用户信息，凭证无效时为 {@code null} */
    LoginUserRespDTO loginUser(String cookie);

    /** 用户信息。 */
    UserInfoRespDTO userInfo();

    /** 用户联系方式。 */
    UserContactRespDTO userContact();

    /** 获取持票人列表。 */
    BearerListRespDTO getBearers(Integer page, Integer pageSize, String eventIds, String exitTicketCert);

    /** 获取联系人 / 收件人列表。 */
    DisListRespDTO getDis(UserGetDisReqDTO query);

    /** 联系人详情。 */
    UserGetDisInfoRespDTO getDisInfo(String disId);

    /** 修改用户信息。 */
    Object modifyUser(UserModifyUserReqDTO form);

    /** 修改联系人。 */
    Object modifyDis(UserModifyDisReqDTO form);

    /** 新增持票人。 */
    UserAddBearerRespDTO addBearer(UserAddBearerReqDTO form);

    /** 删除持票人。 */
    Object removeBearer(UserRemoveBearerReqDTO form);

    /** 新增联系人。 */
    Object addDis(UserAddDisReqDTO form);

    /** 删除联系人。 */
    Object removeDis(UserRemoveDisReqDTO form);

    /** 退出登录。 */
    Object loginOff(UserLoginOffReqDTO form);
}
