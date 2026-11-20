package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuLangTypeEnum;
import cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 租户参数拦截器：追加 {@code site} 头与 {@code __sr}/{@code langType} 查询参数。
 * <p>必须排在 {@link AliyuRiskInterceptor} 之后：这些参数不参与 {@code hecate} 签名，
 * 小程序是在用原始 {@code data} 完成签名后才把它们拼进 URL。
 */
@Component
@RequiredArgsConstructor
public class AliyuTenantInterceptor implements ForestInterceptor {

    @Override
    public boolean beforeExecute(ForestRequest request) {
        request.addHeader(AliyuApiConstants.Header.SITE, AliyuSiteEnum.MP_WEIXIN.getValue());

        request.addQuery(AliyuApiConstants.Query.LANG_TYPE, AliyuLangTypeEnum.SIMPLIFIED_CHINESE.getValue());
        request.addQuery(AliyuApiConstants.Query.__SR, AliyuApiConstants.Gateway.SR_VALUE, true, StandardCharsets.UTF_8.name());
        return true;
    }
}
