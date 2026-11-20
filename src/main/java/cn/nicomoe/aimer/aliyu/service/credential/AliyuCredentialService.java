package cn.nicomoe.aimer.aliyu.service.credential;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuCredentialBO;
import com.dtflys.forest.http.ForestResponse;

/** 阿里鱼运行态凭证服务：CSRF 懒加载、会话/操作者回写、Cookie 合并。 */
public interface AliyuCredentialService {

    /**
     * 为本次请求解析可用的凭证束；CSRF 缺失时同步换取，并顺带刷新会话与操作者 ID。
     *
     * @param cookie 持久会话 Cookie
     * @return 本次请求可用的凭证束
     */
    AliyuCredentialBO resolveForRequest(String cookie);

    /**
     * 从响应中回写短时凭证并合并 {@code Set-Cookie}。
     *
     * @param cookie   本次请求使用的 Cookie
     * @param response 上游响应
     */
    void saveFromResponse(String cookie, ForestResponse<?> response);

    /**
     * 清理某个 Cookie 对应的全部运行态缓存（换绑/解绑时调用）。
     *
     * @param cookie 要清理的 Cookie
     */
    void evictCache(String cookie);
}
