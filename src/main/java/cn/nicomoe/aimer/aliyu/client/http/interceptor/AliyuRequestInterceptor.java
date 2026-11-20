package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuLangTypeEnum;
import cn.nicomoe.aimer.aliyu.enums.AliyuSiteEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuCredentialBO;
import cn.nicomoe.aimer.aliyu.service.credential.AliyuCredentialService;
import cn.nicomoe.aimer.aliyu.service.crypto.AliyuRsaService;
import cn.nicomoe.aimer.aliyu.service.risk.AliyuRiskService;
import com.dtflys.forest.converter.json.ForestJsonConverter;
import com.dtflys.forest.http.ForestBody;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestRequestBody;
import com.dtflys.forest.http.ForestRequestType;
import com.dtflys.forest.http.body.NameValueRequestBody;
import com.dtflys.forest.http.body.ObjectRequestBody;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 请求拦截器：按「风控头 → 租户参数 → 凭证头 → 字段加密」的顺序装饰每个请求。
 */
@Component
@RequiredArgsConstructor
public class AliyuRequestInterceptor implements ForestInterceptor {

    private final AliyuRiskService riskService;
    private final AliyuCredentialService credentialService;
    private final AliyuRsaService rsaService;

    @Override
    public boolean beforeExecute(ForestRequest request) {
        this.applyRiskHeaders(request);
        this.applyTenantParams(request);
        this.applyCredentialHeaders(request);
        this.encryptSensitiveFields(request);
        return true;
    }

    protected void applyRiskHeaders(ForestRequest<?> request) {
        String epeius = riskService.calcEpeius();
        String coeus = riskService.calcCoeus(request.getPath());
        String hecate = riskService.calcHecate(this.plainBody(request), epeius);

        request.addHeader(AliyuApiConstants.RequestHeader.COEUS, coeus);
        request.addHeader(AliyuApiConstants.RequestHeader.EPEIUS, epeius);
        request.addHeader(AliyuApiConstants.RequestHeader.HECATE, hecate);
    }

    protected void applyTenantParams(ForestRequest<?> request) {
        request.addHeader(AliyuApiConstants.RequestHeader.SITE, AliyuSiteEnum.MP_WEIXIN.getValue());

        request.addQuery(AliyuApiConstants.Query.LANG_TYPE, AliyuLangTypeEnum.SIMPLIFIED_CHINESE.getValue());
        request.addQuery(AliyuApiConstants.Query.__SR, AliyuApiConstants.Gateway.SR_VALUE, true, StandardCharsets.UTF_8.name());
    }

    protected void applyCredentialHeaders(ForestRequest<?> request) {
        AliyuCredentialBO credentialBO = credentialService.resolveForRequest(request);
        if (StrUtil.isNotEmpty(credentialBO.getCookie())) {
            request.addHeader(AliyuApiConstants.RequestHeader.COOKIE, credentialBO.getCookie());
        }
        if (StrUtil.isNotEmpty(credentialBO.getSession())) {
            request.addHeader(AliyuApiConstants.RequestHeader.X_MZ_SESSION, credentialBO.getSession());
        }
        if (StrUtil.isNotEmpty(credentialBO.getOperateId())) {
            request.addHeader(AliyuApiConstants.RequestHeader.MZOPERATEID, credentialBO.getOperateId());
        }
        if (StrUtil.isNotEmpty(credentialBO.getCsrf())) {
            request.addHeader(AliyuApiConstants.RequestHeader.X_XSRF_TOKEN, credentialBO.getCsrf());
        }
    }

    protected void encryptSensitiveFields(ForestRequest<?> request) {
        Set<String> fields = AliyuApiConstants.Rsa.ENCRYPT_FIELDS.get(request.getPath());
        if (CollUtil.isEmpty(fields)) {
            return;
        }
        ForestBody body = request.getBody();
        if (Objects.isNull(body)) {
            return;
        }
        for (ForestRequestBody item : body) {
            if (item instanceof ObjectRequestBody objectItem && Objects.nonNull(objectItem.getObject())) {
                ForestJsonConverter jsonConverter = request.getConfiguration().getJsonConverter();
                Object copy = jsonConverter.convertToJavaObject(jsonConverter.encodeToString(objectItem.getObject()), objectItem.getObject().getClass());
                rsaService.encryptFields(copy, fields);
                objectItem.setObject(copy);
            }
        }
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
