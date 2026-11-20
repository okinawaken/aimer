package cn.nicomoe.aimer.aliyu.service.credential;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuCredentialBO;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;

/**
 * 阿里鱼运行态凭证服务：CSRF 懒加载、会话/操作者回写、Cookie 合并。
 */
public interface AliyuCredentialService {

    /**
     * 为本次请求解析可用的凭证束；CSRF 缺失时同步换取，并顺带刷新会话与操作者 ID。
     *
     * @param request 当前请求
     * @return 本次请求可用的凭证束
     */
    AliyuCredentialBO resolveForRequest(ForestRequest<?> request);

    /**
     * 从响应中回写短时凭证并合并 {@code Set-Cookie}。
     *
     * @param request  本次请求
     * @param response 上游响应
     */
    void saveFromResponse(ForestRequest<?> request, ForestResponse<?> response);
}
