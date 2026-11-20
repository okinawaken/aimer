package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.service.credential.AliyuCredentialService;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 响应拦截器：回写服务端下发的短时凭证并合并 {@code Set-Cookie}。
 */
@Component
@RequiredArgsConstructor
public class AliyuResponseInterceptor implements ForestInterceptor {

    private final AliyuCredentialService credentialService;

    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
        credentialService.saveFromResponse(request, response);
    }
}
