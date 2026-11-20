package cn.nicomoe.aimer.aliyu.client.http.v3;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.LoginUserRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Header;

/** 用户域（显式凭证上下文），供凭证尚未落库的绑定校验等场景使用。 */
public interface AliyuUserV3Client extends AliyuBaseClient {

    /** 获取登录用户信息。凭证无效时上游仍返回 {@code code="200"}，调用方需以 {@code data} 是否为空判断。 参数 cookie：持久会话 Cookie */
    @Get(url = "/user/loginUser")
    AliyuClientResult<LoginUserRespDTO> loginUser(@Header(value = AliyuApiConstants.Header.COOKIE) String cookie);
}
