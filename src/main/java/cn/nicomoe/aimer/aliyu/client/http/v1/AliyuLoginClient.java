package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuAuthLoginClientReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.LoginAuthLoginRespDTO;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;

/**
 * 登录鉴权域，走阿里安全 mpassport 登录 SDK。
 */
public interface AliyuLoginClient extends AliyuBaseClient {

    /**
     * 小程序授权登录。
     */
    @Post(url = "/login/authLoginForApplet")
    AliyuClientResult<LoginAuthLoginRespDTO> authLoginForApplet(@JSONBody AliyuAuthLoginClientReqDTO form);
}
