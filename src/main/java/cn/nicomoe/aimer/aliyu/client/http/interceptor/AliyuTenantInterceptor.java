package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.ForestInterceptor;

import java.nio.charset.StandardCharsets;

/** 为不经过 {@link AliyuAuthInterceptor} 的请求追加租户参数。 <p> 登录、注册与站点配置查询发生在账号绑定之前，拿不到用户配置，只需按固定租户地址生成 {@code __sr}。 与鉴权拦截器保持一致，值开启 URL 编码。 */
public class AliyuTenantInterceptor implements ForestInterceptor {

    @Override
    public boolean beforeExecute(ForestRequest request) {
        request.addQuery(AliyuApiConstants.Query.__SR, AliyuApiConstants.Gateway.SR_VALUE, true, StandardCharsets.UTF_8.name());
        return true;
    }
}
