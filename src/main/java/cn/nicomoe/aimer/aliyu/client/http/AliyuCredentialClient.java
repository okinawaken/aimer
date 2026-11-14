package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.resp.LoginUserRespDTO;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Header;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.URLEncode;

/**
 * 凭证校验接口（绑定前使用）。
 * <p>
 * <b>刻意不继承 {@link AliyuBaseClient}</b>：绑定流程中新凭证尚未落库，鉴权拦截器读不到配置，
 * 会因强制鉴权直接拒绝。此处由调用方显式传入待校验的凭证束。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json")
public interface AliyuCredentialClient {

    /**
     * 校验凭证束是否有效（成功返回登录用户信息）。
     *
     * @param cookie    持久会话 Cookie
     * @param operateId 操作者 ID
     * @param session   maitix 会话标识
     * @param site      渠道标识
     * @param sr        租户反解析参数
     * @return 登录用户信息
     */
    @Get(url = "/user/loginUser")
    AliyuClientResult<LoginUserRespDTO> loginUser(
            @Header(AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(AliyuApiConstants.Header.MZOPERATEID) String operateId,
            @Header(AliyuApiConstants.Header.X_MZ_SESSION) String session,
            @Header(AliyuApiConstants.Header.SITE) String site,
            @URLEncode @Query(AliyuApiConstants.Query.__SR) String sr);
}
