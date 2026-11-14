package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuAuthLoginClientReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.LoginAuthLoginRespDTO;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;

/** 登录鉴权域：{@code /login}，走阿里安全 mpassport 登录 SDK。 <p> {@code authCode}/{@code jsCode} 是 {@code wx.login()} 的 code（不加密）；{@code encryptedData} / {@code iv} 来自微信 {@code wx.getPhoneNumber} 回调，需真机授权或抓包提供。成功后服务端下发 cookie 与会话头。 */
public interface AliyuLoginClient extends AliyuBaseClient {

    /** 小程序授权登录（加密）。成功后服务端写入 cookie（JSESSIONID / MZCONSUMERJSESSIONID / MZOPERATEID）， 后续请求携带完整凭证束。 */
    @Post(url = "/login/authLoginForApplet")
    AliyuClientResult<LoginAuthLoginRespDTO> authLoginForApplet(@JSONBody AliyuAuthLoginClientReqDTO form);
}
