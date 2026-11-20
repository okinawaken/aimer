package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuUserClient;
import cn.nicomoe.aimer.aliyu.client.http.v3.AliyuUserV3Client;
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
import cn.nicomoe.aimer.aliyu.service.client.AliyuUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/** 阿里鱼用户域服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuUserServiceImpl implements AliyuUserService {

    private final AliyuUserClient userClient;
    private final AliyuUserV3Client userV3Client;

    @Override
    public LoginUserRespDTO loginUser() {
        AliyuClientResult<LoginUserRespDTO> result = userClient.loginUser();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public LoginUserRespDTO loginUser(String cookie) {
        AliyuClientResult<LoginUserRespDTO> result = userV3Client.loginUser(cookie);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public UserInfoRespDTO userInfo() {
        AliyuClientResult<UserInfoRespDTO> result = userClient.userInfo();
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
    public BearerListRespDTO getBearers(Integer page, Integer pageSize, String eventIds, String exitTicketCert) {
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
    public Object modifyUser(UserModifyUserReqDTO form) {
        AliyuClientResult<Object> result = userClient.modifyUser(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object modifyDis(UserModifyDisReqDTO form) {
        AliyuClientResult<Object> result = userClient.modifyDis(form);
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
    public Object removeBearer(UserRemoveBearerReqDTO form) {
        AliyuClientResult<Object> result = userClient.removeBearer(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object addDis(UserAddDisReqDTO form) {
        AliyuClientResult<Object> result = userClient.addDis(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object removeDis(UserRemoveDisReqDTO form) {
        AliyuClientResult<Object> result = userClient.removeDis(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object loginOff(UserLoginOffReqDTO form) {
        AliyuClientResult<Object> result = userClient.loginOff(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
