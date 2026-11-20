package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuAuthLoginClientReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.LoginAuthLoginRespDTO;

/**
 * 阿里鱼登录鉴权域服务，返回已解包的业务数据。
 */
public interface AliyuLoginService {

    /**
     * 小程序授权登录。
     */
    LoginAuthLoginRespDTO authLoginForApplet(AliyuAuthLoginClientReqDTO form);
}
