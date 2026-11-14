package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.crypto.AliyuFieldEncryptor;
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

/**
 * 阿里鱼请求体字段加密拦截器。
 * <p>
 * 命中 {@link #ENCRYPT_FIELD_RULES} 的接口，在发送前把敏感字段（手机号、密码、邮箱、身份证等）
 * 原地替换为 RSA-2048 密文，等价小程序 request 封装内的 {@code U(url, data)} 拦截逻辑；
 * 未命中接口与未命中字段保持明文，避免无谓的序列化开销。
 * </p>
 * <p>
 * 选择在 {@code beforeExecute} 处理而非序列化之后改写字节，是为了保留字段原始类型与 Map 结构，
 * 不引入 JSON 反序列化往返带来的命名 / 精度变化。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@Component
@RequiredArgsConstructor
public class AliyuEncryptInterceptor implements ForestInterceptor {

    /**
     * 请求附件名：标记该请求体已完成加密，防止同一请求对象被重复执行时二次加密
     */
    private static final String ATTACHMENT_ENCRYPTED = "aliyu.encrypted";

    /**
     * 接口路径 → 需 RSA 加密的字段名集合（源码 R 表，16 条）。
     * <p>
     * 路径为相对路径（不含 {@code /api} 前缀），与 {@code @Post(url = "...")} 中声明的 urlTemplate 一致。
     * </p>
     */
    private static final Map<String, Set<String>> ENCRYPT_FIELD_RULES = Map.ofEntries(
            Map.entry("/user/userLogin", Set.of("loginPass", "phone", "email")),
            Map.entry("/user/validateUserV2", Set.of("userEmail", "loginPass", "userTel", "contactInfo")),
            Map.entry("/user/registerSetPass", Set.of("loginPass", "userTel", "userEmail", "userCard",
                    "emergencyContact", "emergencyContactTelphone")),
            Map.entry("/user/modifyUser", Set.of("userCard", "emergencyContact", "emergencyContactTelphone")),
            Map.entry("/user/findPass", Set.of("mobile", "email")),
            Map.entry("/user/findPassCheck", Set.of("newPass")),
            Map.entry("/user/resetMobileOrEmail", Set.of("email", "phone")),
            Map.entry("/user/resetPwdValidate", Set.of("mobile", "email")),
            Map.entry("/user/resetPwdSet", Set.of("newPass")),
            Map.entry("/user/addDis", Set.of("email", "telPhone")),
            Map.entry("/user/modifyDis", Set.of("email", "telPhone")),
            Map.entry("/user/addBearer", Set.of("identification")),
            Map.entry("/msg/sendMobileMsg", Set.of("telephone")),
            Map.entry("/msg/sendEmailMsg", Set.of("email")),
            Map.entry("/trade/submitGroupedCart", Set.of("receiveCard", "receiveName", "paymentPassword")),
            Map.entry("/invoice/simple/applyInvoice", Set.of("buyerTel", "email"))
    );

    private final AliyuFieldEncryptor fieldEncryptor;

    @Override
    public boolean beforeExecute(ForestRequest request) {
        Set<String> fields = ENCRYPT_FIELD_RULES.get(request.getPath());
        // 小程序只加密 POST 请求的 JSON 请求体（GET 的 data 被拼进 query，不参与加解密）
        if (Objects.isNull(fields) || !ForestRequestType.POST.equals(request.getType())
                || Objects.nonNull(request.getAttachment(ATTACHMENT_ENCRYPTED))) {
            return true;
        }
        ForestBody body = request.getBody();
        if (Objects.isNull(body) || body.isEmpty()) {
            return true;
        }
        for (ForestRequestBody item : body) {
            this.encryptItem(request, item, fields);
        }
        request.addAttachment(ATTACHMENT_ENCRYPTED, Boolean.TRUE);
        return true;
    }

    /**
     * 按请求体项类型加密：键值对逐字段处理，对象请求体整体递归处理。
     *
     * @param request Forest 请求
     * @param item    请求体项
     * @param fields  该接口需加密的字段名集合
     */
    private void encryptItem(ForestRequest<?> request, ForestRequestBody item, Set<String> fields) {
        if (item instanceof NameValueRequestBody nameValueItem) {
            Object value = nameValueItem.getValue();
            Object encrypted = this.fieldEncryptor.encryptEntry(nameValueItem.getName(), value, fields);
            if (!Objects.equals(encrypted, value)) {
                nameValueItem.setValue(encrypted);
            }
        } else if (item instanceof ObjectRequestBody objectItem) {
            Object encrypted = this.fieldEncryptor.encryptBodyObject(objectItem.getObject(), fields,
                    obj -> request.getConfiguration().getJsonConverter().convertObjectToMap(obj, request));
            if (Objects.nonNull(encrypted)) {
                objectItem.setObject(encrypted);
            }
        }
    }
}