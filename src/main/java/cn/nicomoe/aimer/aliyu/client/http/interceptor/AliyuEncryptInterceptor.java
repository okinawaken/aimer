package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.util.AliyuRequestUtils;
import cn.nicomoe.aimer.aliyu.service.crypto.AliyuEncryptService;
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
import java.util.Set;

/** 阿里鱼请求体字段加密拦截器，在序列化前按字段规则加密敏感值。 */
@Component
@RequiredArgsConstructor
public class AliyuEncryptInterceptor implements ForestInterceptor {

    /** 接口路径与 RSA 加密字段的映射，路径不含 {@code /api} 前缀。 */
    private static final Map<String, Set<String>> ENCRYPT_FIELD_RULES = Map.ofEntries(
            Map.entry("/user/userLogin", Set.of("loginPass", "phone", "email")),
            Map.entry("/user/validateUserV2", Set.of("userEmail", "loginPass", "userTel", "contactInfo")),
            Map.entry("/user/registerSetPass", Set.of("loginPass", "userTel", "userEmail", "userCard", "emergencyContact", "emergencyContactTelphone")),
            Map.entry("/user/findPass", Set.of("mobile", "email")),
            Map.entry("/user/findPassCheck", Set.of("newPass")),
            Map.entry("/user/modifyUser", Set.of("userCard", "emergencyContact", "emergencyContactTelphone")),
            Map.entry("/user/addDis", Set.of("email", "telPhone")),
            Map.entry("/user/modifyDis", Set.of("email", "telPhone")),
            Map.entry("/user/addBearer", Set.of("identification")),
            Map.entry("/msg/sendMobileMsg", Set.of("telephone")),
            Map.entry("/msg/sendEmailMsg", Set.of("email")),
            Map.entry("/trade/submitGroupedCart", Set.of("receiveCard", "receiveName", "paymentPassword")),
            Map.entry("/invoice/simple/applyInvoice", Set.of("buyerTel", "email"))
    );

    private final AliyuEncryptService encryptService;

    @Override
    public boolean beforeExecute(ForestRequest request) {
        Set<String> fields = ENCRYPT_FIELD_RULES.get(request.getPath());
        // 小程序只加密 POST 请求的 JSON 请求体（GET 的 data 被拼进 query，不参与加解密）
        if (Objects.isNull(fields) || !Objects.equals(ForestRequestType.POST, request.getType())
                || Objects.nonNull(request.getAttachment(AliyuRequestUtils.ATTACHMENT_ENCRYPTED))) {
            return true;
        }
        ForestBody body = request.getBody();
        if (Objects.isNull(body) || body.isEmpty()) {
            return true;
        }
        for (ForestRequestBody item : body) {
            this.encryptItem(request, item, fields);
        }
        request.addAttachment(AliyuRequestUtils.ATTACHMENT_ENCRYPTED, Boolean.TRUE);
        return true;
    }

    private void encryptItem(ForestRequest<?> request, ForestRequestBody item, Set<String> fields) {
        if (item instanceof NameValueRequestBody nameValueItem) {
            Object value = nameValueItem.getValue();
            Object encrypted = this.encryptService.encryptEntry(nameValueItem.getName(), value, fields);
            if (!Objects.equals(encrypted, value)) {
                nameValueItem.setValue(encrypted);
            }
        } else if (item instanceof ObjectRequestBody objectItem) {
            Object encrypted = this.encryptService.encryptBodyObject(objectItem.getObject(), fields,
                    obj -> AliyuRequestUtils.toMap(request, obj));
            if (Objects.nonNull(encrypted)) {
                objectItem.setObject(encrypted);
            }
        }
    }
}