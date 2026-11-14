package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.util.AliyuRequestUtils;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuLangTypeEnum;
import com.dtflys.forest.http.ForestBody;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestRequestBody;
import com.dtflys.forest.http.ForestRequestType;
import com.dtflys.forest.http.body.ObjectRequestBody;
import com.dtflys.forest.interceptor.ForestInterceptor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** 统一追加 {@code langType}，与小程序行为保持一致。 <p> 小程序对每个请求 URL 末尾追加 {@code langType}（简体 1、英文 2、繁体 3，默认简体 1）； 对 POST 的非数组对象请求体，还会把 {@code langType} 写入 body。写入发生在风控签名之后， 因此 {@code hecate} 仍是对不含 {@code langType} 的明文签名，与小程序一致。 若调用方已显式传入 {@code langType}（query 或 body），此处不覆盖。 */
public class AliyuLangTypeInterceptor implements ForestInterceptor {

    @Override
    public boolean beforeExecute(ForestRequest request) {
        String langType = this.langType(request);
        this.addBodyLangType(request, langType);
        return true;
    }

    private String langType(ForestRequest<?> request) {
        Object existing = request.getQuery(AliyuApiConstants.Query.LANG_TYPE);
        if (Objects.isNull(existing) || existing.toString().isBlank()) {
            String defaultLangType = AliyuLangTypeEnum.SIMPLIFIED_CHINESE.getValue();
            request.addQuery(AliyuApiConstants.Query.LANG_TYPE, defaultLangType);
            return defaultLangType;
        }
        return existing.toString();
    }

    /** 小程序仅当 POST 请求体是非数组对象时才追加 {@code langType}。 */
    private void addBodyLangType(ForestRequest<?> request, String langType) {
        if (!Objects.equals(ForestRequestType.POST, request.getType())) {
            return;
        }
        ForestBody body = request.getBody();
        if (Objects.isNull(body) || body.isEmpty()) {
            return;
        }
        for (ForestRequestBody item : body) {
            if (!(item instanceof ObjectRequestBody objectItem)) {
                continue;
            }
            Map<String, Object> bodyMap = this.toBodyMap(request, objectItem.getObject());
            if (Objects.isNull(bodyMap) || bodyMap.containsKey(AliyuApiConstants.Query.LANG_TYPE)) {
                continue;
            }
            bodyMap.put(AliyuApiConstants.Query.LANG_TYPE, langType);
            objectItem.setObject(bodyMap);
        }
    }

    /** 数组请求体不追加；Map 直接拷贝（避免修改不可变 Map），DTO 用 Forest 的 JSON 转换器转成 Map。 */
    private Map<String, Object> toBodyMap(ForestRequest<?> request, Object object) {
        if (Objects.isNull(object) || object instanceof List<?> || object.getClass().isArray()
                || object instanceof CharSequence || object instanceof Number
                || object instanceof Boolean || object instanceof Character || object instanceof Enum<?>) {
            return null;
        }
        Map<?, ?> source = object instanceof Map<?, ?> map
                ? map
                : AliyuRequestUtils.toMap(request, object);
        // 转换器可能返回不可变 Map，统一拷贝后再写入
        return Objects.isNull(source) ? null : this.copy(source);
    }

    private Map<String, Object> copy(Map<?, ?> map) {
        Map<String, Object> result = new LinkedHashMap<>(map.size() + 1);
        map.forEach((key, value) -> result.put(String.valueOf(key), value));
        return result;
    }
}
