package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.hutool.core.map.MapUtil;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.service.risk.AliyuRiskService;
import com.dtflys.forest.http.ForestBody;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestRequestBody;
import com.dtflys.forest.http.ForestRequestType;
import com.dtflys.forest.http.body.NameValueRequestBody;
import com.dtflys.forest.http.body.ObjectRequestBody;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 风控头拦截器，按请求明文计算 {@code coeus}/{@code epeius}/{@code hecate}。
 * <p>必须在租户参数（{@code __sr}/{@code langType}）与字段加密之前执行：小程序用调用方传入的原始
 * {@code data} 计算签名，GET 的原始 {@code data} 就是业务查询参数，不包含框架追加的 {@code __sr}/{@code langType}。
 */
@Component
@RequiredArgsConstructor
public class AliyuRiskInterceptor implements ForestInterceptor {

    private final AliyuRiskService riskService;

    @Override
    public boolean beforeExecute(ForestRequest request) {
        String coeus = riskService.calcCoeus(request.getPath());
        String epeius = riskService.calcEpeius();
        String hecate = riskService.calcHecate(this.plainBody(request), epeius);

        request.addHeader(AliyuApiConstants.Header.COEUS, coeus);
        request.addHeader(AliyuApiConstants.Header.EPEIUS, epeius);
        request.addHeader(AliyuApiConstants.Header.HECATE, hecate);
        return true;
    }

    private Map<String, Object> plainBody(ForestRequest<?> request) {
        if (Objects.equals(request.getType(), ForestRequestType.GET)) {
            return request.getQuery();
        }
        ForestBody body = request.getBody();
        Map<String, Object> data = MapUtil.newHashMap(body.size());
        for (ForestRequestBody item : body) {
            if (item instanceof NameValueRequestBody nameValueItem) {
                data.put(nameValueItem.getName(), nameValueItem.getValue());
            } else if (item instanceof ObjectRequestBody objectItem && Objects.nonNull(objectItem.getObject())) {
                Map<String, Object> bodyMap = request.getConfiguration().getJsonConverter().convertObjectToMap(objectItem.getObject(), request);
                if (Objects.nonNull(bodyMap)) {
                    data.putAll(bodyMap);
                }
            }
        }
        return data;
    }
}
