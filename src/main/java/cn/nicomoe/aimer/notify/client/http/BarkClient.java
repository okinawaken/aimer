package cn.nicomoe.aimer.notify.client.http;

import cn.nicomoe.aimer.notify.model.dto.BarkPushReqDTO;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Var;
import com.dtflys.forest.http.ForestResponse;

/**
 * Bark 推送 Forest 客户端。
 * <p>推送地址完整由调用方通过 {@code barkUrl} 传入（服务器与设备 Key 合并，形如 {@code https://api.day.app/{key}}），
 * 不绑定固定 baseURL，也不复用自习室接口的鉴权拦截器。</p>
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface BarkClient {

    /**
     * 向 {@code barkUrl} 推送一条 Bark 通知。
     *
     * @param barkUrl 完整推送地址，形如 {@code https://api.day.app/{key}}
     * @param payload 请求体（title / body / group）
     * @return Forest 响应，用于调用方判断状态码
     */
    @Post(url = "{barkUrl}")
    ForestResponse<?> push(@Var(value = "barkUrl") String barkUrl, @JSONBody BarkPushReqDTO payload);
}
