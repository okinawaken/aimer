package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.AliyuUserCheckClient;
import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuUserClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuLangTypeEnum;
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
import cn.nicomoe.aimer.aliyu.model.dto.UserInfoReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserLoginOffReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserModifyDisReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserModifyUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserRemoveBearerReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserRemoveDisReqDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 阿里鱼用户域服务实现。
 */
@Service
@RequiredArgsConstructor
public class AliyuUserServiceImpl implements AliyuUserService {

    private final AliyuUserClient userClient;

    private final AliyuUserCheckClient userCheckClient;

    @Override
    public LoginUserRespDTO loginUser() {
        AliyuClientResult<LoginUserRespDTO> result = userClient.loginUser();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public LoginUserRespDTO loginUser(String session) {
        AliyuClientResult<LoginUserRespDTO> result = userCheckClient.loginUser(session, AliyuLangTypeEnum.SIMPLIFIED_CHINESE.getValue(), AliyuApiConstants.Gateway.SR_VALUE);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public UserInfoRespDTO userInfo(UserInfoReqDTO query) {
        AliyuClientResult<UserInfoRespDTO> result = userClient.userInfo(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public UserInfoRespDTO userInfo(String session) {
        AliyuClientResult<UserInfoRespDTO> result = userCheckClient.userInfo(session, AliyuLangTypeEnum.SIMPLIFIED_CHINESE.getValue(), AliyuApiConstants.Gateway.SR_VALUE);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public UserContactRespDTO userContact() {
        AliyuClientResult<UserContactRespDTO> result = userClient.userContact();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public BearerListRespDTO getBearers(String page, String pageSize, String eventIds, String exitTicketCert) {
        AliyuClientResult<BearerListRespDTO> result = userClient.getBearers(page, pageSize, eventIds, exitTicketCert);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public DisListRespDTO getDis(UserGetDisReqDTO query) {
        AliyuClientResult<DisListRespDTO> result = userClient.getDis(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public UserGetDisInfoRespDTO getDisInfo(String disId) {
        AliyuClientResult<UserGetDisInfoRespDTO> result = userClient.getDisInfo(disId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean modifyUser(UserModifyUserReqDTO form) {
        AliyuClientResult<Boolean> result = userClient.modifyUser(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean modifyDis(UserModifyDisReqDTO form) {
        AliyuClientResult<Boolean> result = userClient.modifyDis(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public UserAddBearerRespDTO addBearer(UserAddBearerReqDTO form) {
        AliyuClientResult<UserAddBearerRespDTO> result = userClient.addBearer(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean removeBearer(UserRemoveBearerReqDTO form) {
        AliyuClientResult<Boolean> result = userClient.removeBearer(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean addDis(UserAddDisReqDTO form) {
        AliyuClientResult<Boolean> result = userClient.addDis(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean removeDis(UserRemoveDisReqDTO form) {
        AliyuClientResult<Boolean> result = userClient.removeDis(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean loginOff(UserLoginOffReqDTO form) {
        AliyuClientResult<Boolean> result = userClient.loginOff(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
