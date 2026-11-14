package cn.nicomoe.aimer.aliyu.client.http.v3;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.LoginUserRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Var;

/** 用户域（显式凭证上下文）。 <p> 与 v2 的差别只在凭证来源：凭证由调用方通过 {@code @Var} 直接传入， {@code AliyuAuthInterceptor} 不再查库、也不要求登录上下文。 供凭证尚未落库的场景使用——例如绑定前校验新凭证束是否有效。 */
public interface AliyuUserV3Client extends AliyuBaseClient {

    /** 获取登录用户信息。 <p> 凭证无效时上游仍返回 {@code code="200"}，调用方需以 {@code data} 是否为空判断登录状态。 参数 cookie：持久会话 Cookie 参数 sessValue：maitix 会话标识（{@code X-MZ-SESSION} 头） */
    @Get(url = "/user/loginUser")
    AliyuClientResult<LoginUserRespDTO> loginUser(@Var(value = AliyuApiConstants.Variable.COOKIE) String cookie, @Var(value = AliyuApiConstants.Variable.SESS_VALUE) String sessValue);
}