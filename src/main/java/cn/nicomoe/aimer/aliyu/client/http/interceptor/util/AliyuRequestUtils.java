package cn.nicomoe.aimer.aliyu.client.http.interceptor.util;

import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import com.dtflys.forest.http.ForestBody;
import com.dtflys.forest.http.ForestQueryMap;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestRequestBody;
import com.dtflys.forest.http.ForestRequestType;
import com.dtflys.forest.http.body.NameValueRequestBody;
import com.dtflys.forest.http.body.ObjectRequestBody;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** 拦截器共享的请求附件键与请求体转换工具。 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliyuRequestUtils {

    /** 标记请求体已加密，防止同一请求重复执行时二次加密。 */
    public static final String ATTACHMENT_ENCRYPTED = "aliyu.encrypted";

    /** 缓存本次请求已解析的用户凭证，避免响应阶段重复查库，也避免凭证解析异常掩盖真实响应。 */
    public static final String ATTACHMENT_USER_CONFIG = "aliyu.userConfig";

    /** 读取明文请求数据，使用 Forest JSON 转换器保持序列化字段一致。 无请求体时，GET 使用查询参数并排除 {@code __sr}；其他方法返回空 Map。 */
    public static Map<String, Object> plainBody(ForestRequest<?> request) {
        ForestBody body = request.getBody();
        if (Objects.isNull(body) || body.isEmpty()) {
            return queryData(request);
        }
        Map<String, Object> data = new LinkedHashMap<>(body.size());
        for (ForestRequestBody item : body) {
            if (item instanceof NameValueRequestBody nameValueItem) {
                data.put(nameValueItem.getName(), nameValueItem.getValue());
            } else if (item instanceof ObjectRequestBody objectItem && Objects.nonNull(objectItem.getObject())) {
                Map<String, Object> bodyMap = toMap(request, objectItem.getObject());
                if (Objects.nonNull(bodyMap)) {
                    data.putAll(bodyMap);
                }
            }
        }
        return data;
    }

    /** 用 Forest 的 JSON 转换器把对象请求体转成 Map，保证加密与签名看到的字段集合与最终序列化一致。 返回：转换结果；转换器返回 {@code null} 时原样返回 */
    public static Map<String, Object> toMap(ForestRequest<?> request, Object body) {
        return request.getConfiguration().getJsonConverter().convertObjectToMap(body, request);
    }

    private static Map<String, Object> queryData(ForestRequest<?> request) {
        if (!Objects.equals(request.getType(), ForestRequestType.GET)) {
            return Map.of();
        }
        ForestQueryMap query = request.getQuery();
        if (Objects.isNull(query) || query.isEmpty()) {
            return Map.of();
        }
        Map<String, Object> data = new LinkedHashMap<>(query.size());
        query.forEach((name, value) -> {
            if (!Objects.equals(AliyuApiConstants.Query.__SR, name)) {
                data.put(name, value);
            }
        });
        return data;
    }
}
