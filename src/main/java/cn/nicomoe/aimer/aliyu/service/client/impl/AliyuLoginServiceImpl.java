package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuLoginClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuAuthLoginClientReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.LoginAuthLoginRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/** 阿里鱼登录鉴权域服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuLoginServiceImpl implements AliyuLoginService {

    private final AliyuLoginClient loginClient;

    @Override
    public LoginAuthLoginRespDTO authLoginForApplet(AliyuAuthLoginClientReqDTO form) {
        AliyuClientResult<LoginAuthLoginRespDTO> result = loginClient.authLoginForApplet(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
