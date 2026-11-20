package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.AliyuValidateResponseInterceptor;
import cn.nicomoe.aimer.aliyu.client.http.provider.AliyuOkHttpClientProvider;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.LoginUserRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserInfoRespDTO;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Header;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Redirection;
import com.dtflys.forest.backend.okhttp3.OkHttp3;

/**
 * 用户域。
 */
@Redirection(false)
@OkHttp3(client = AliyuOkHttpClientProvider.class)
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json", interceptor = {AliyuValidateResponseInterceptor.class})
public interface AliyuUserCheckClient {

    /**
     * 获取登录用户信息，主动传入凭证头，绕过运行态 Redis 凭证。
     */
    @Get(url = "/user/loginUser")
    AliyuClientResult<LoginUserRespDTO> loginUser(@Header(value = AliyuApiConstants.RequestHeader.X_MZ_SESSION) String session,
                                                  @Query(value = AliyuApiConstants.Query.LANG_TYPE) String langType,
                                                  @Query(value = AliyuApiConstants.Query.__SR, defaultValue = AliyuApiConstants.Gateway.SR_VALUE) String __sr);

    /**
     * 获取用户信息，主动传入凭证头，绕过运行态 Redis 凭证。
     */
    @Get(url = "/user/userInfo")
    AliyuClientResult<UserInfoRespDTO> userInfo(@Header(value = AliyuApiConstants.RequestHeader.X_MZ_SESSION) String session,
                                                @Query(value = AliyuApiConstants.Query.LANG_TYPE) String langType,
                                                @Query(value = AliyuApiConstants.Query.__SR, defaultValue = AliyuApiConstants.Gateway.SR_VALUE) String __sr);
}
