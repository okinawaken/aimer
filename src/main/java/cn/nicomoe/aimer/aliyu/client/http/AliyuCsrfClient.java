package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Header;

/**
 * CSRF token 获取接口（{@code GET /getCsrfToken}，TTL 60s）。
 * <p>
 * <b>刻意不继承 {@link AliyuBaseClient}</b>：避免鉴权拦截器在获取 CSRF 时递归调用自身。
 * 仅携带调用方显式传入的持久会话 Cookie 与操作者 ID。
 * </p>
 * <p>
 * 启动链路的两个引导接口（本接口与 {@code /partner/queryMDomain}）都不追加 {@code __sr}，
 * 租户反解析参数仅作用于业务接口。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@BaseRequest(baseURL = AliyuApiConstants.Gateway.BASE_URL, contentType = "application/json")
public interface AliyuCsrfClient {

    /**
     * 获取 CSRF token。
     *
     * @param cookie    持久会话 Cookie
     * @param operateId 操作者 ID
     * @param site      渠道标识
     * @return CSRF token 响应
     */
    @Get(url = AliyuApiConstants.Bootstrap.GET_CSRF_TOKEN)
    AliyuClientResult<AliyuCsrfTokenRespDTO> getCsrfToken(
            @Header(AliyuApiConstants.Header.COOKIE) String cookie,
            @Header(AliyuApiConstants.Header.MZOPERATEID) String operateId,
            @Header(AliyuApiConstants.Header.SITE) String site);
}
