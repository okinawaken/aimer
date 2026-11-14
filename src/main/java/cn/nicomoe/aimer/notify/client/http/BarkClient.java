package cn.nicomoe.aimer.notify.client.http;

import cn.nicomoe.aimer.notify.model.dto.BarkPushReqDTO;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Var;
import com.dtflys.forest.http.ForestResponse;

/** Bark 推送客户端。地址由调用方传入（形如 {@code https://api.day.app/{key}}）， 不绑定 baseURL，也不复用自习室接口的鉴权拦截器。 */
public interface BarkClient {

    /** 向 {@code barkUrl} 推送一条 Bark 通知。 参数 barkUrl：完整推送地址，形如 {@code https://api.day.app/{key}} 参数 payload：请求体（title / body / group） 返回：Forest 响应，用于调用方判断状态码 */
    @Post(url = "{barkUrl}")
    ForestResponse<?> push(@Var(value = "barkUrl") String barkUrl, @JSONBody BarkPushReqDTO payload);
}
