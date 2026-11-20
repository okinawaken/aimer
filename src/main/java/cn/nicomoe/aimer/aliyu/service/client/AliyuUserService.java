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
import cn.nicomoe.aimer.aliyu.model.dto.UserInfoReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserLoginOffReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserModifyDisReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserModifyUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserRemoveBearerReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserRemoveDisReqDTO;

/**
 * 阿里鱼用户域服务，返回已解包的业务数据。
 */
public interface AliyuUserService {

    /**
     * 获取登录用户信息。
     */
    LoginUserRespDTO loginUser();

    /**
     * 获取登录用户信息。
     */
    LoginUserRespDTO loginUser(String session);

    /**
     * 用户信息。
     */
    UserInfoRespDTO userInfo(UserInfoReqDTO query);

    /**
     * 使用指定凭证获取用户信息，不依赖运行态缓存。
     */
    UserInfoRespDTO userInfo(String session);

    /**
     * 用户联系方式。
     */
    UserContactRespDTO userContact();

    /**
     * 获取持票人列表。
     */
    BearerListRespDTO getBearers(String page, String pageSize, String eventIds, String exitTicketCert);

    /**
     * 获取联系人 / 收件人列表。
     */
    DisListRespDTO getDis(UserGetDisReqDTO query);

    /**
     * 联系人详情。
     */
    UserGetDisInfoRespDTO getDisInfo(String disId);

    /**
     * 修改用户信息。
     */
    Boolean modifyUser(UserModifyUserReqDTO form);

    /**
     * 修改联系人。
     */
    Boolean modifyDis(UserModifyDisReqDTO form);

    /**
     * 新增持票人。
     */
    UserAddBearerRespDTO addBearer(UserAddBearerReqDTO form);

    /**
     * 删除持票人。
     */
    Boolean removeBearer(UserRemoveBearerReqDTO form);

    /**
     * 新增联系人。
     */
    Boolean addDis(UserAddDisReqDTO form);

    /**
     * 删除联系人。
     */
    Boolean removeDis(UserRemoveDisReqDTO form);

    /**
     * 退出登录。
     */
    Boolean loginOff(UserLoginOffReqDTO form);
}
