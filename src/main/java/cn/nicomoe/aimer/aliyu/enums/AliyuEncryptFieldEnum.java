package cn.nicomoe.aimer.aliyu.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 接口路径与 RSA 加密字段的映射。
 */
@Getter
@RequiredArgsConstructor
public enum AliyuEncryptFieldEnum implements BaseEnum<String> {

    USER_LOGIN("/user/userLogin", "用户登录", Set.of("loginPass", "phone", "email")),
    VALIDATE_USER_V2("/user/validateUserV2", "用户校验", Set.of("userEmail", "loginPass", "userTel", "contactInfo")),
    REGISTER_SET_PASS("/user/registerSetPass", "注册设置密码", Set.of("loginPass", "userTel", "userEmail", "userCard", "emergencyContact", "emergencyContactTelphone")),
    FIND_PASS("/user/findPass", "找回密码", Set.of("mobile", "email")),
    FIND_PASS_CHECK("/user/findPassCheck", "找回密码校验", Set.of("newPass")),
    MODIFY_USER("/user/modifyUser", "修改用户信息", Set.of("userCard", "emergencyContact", "emergencyContactTelphone")),
    ADD_DIS("/user/addDis", "新增联系人", Set.of("email", "telPhone")),
    MODIFY_DIS("/user/modifyDis", "修改联系人", Set.of("email", "telPhone")),
    ADD_BEARER("/user/addBearer", "新增持票人", Set.of("identification")),
    SEND_MOBILE_MSG("/msg/sendMobileMsg", "发送短信", Set.of("telephone")),
    SEND_EMAIL_MSG("/msg/sendEmailMsg", "发送邮件", Set.of("email")),
    SUBMIT_GROUPED_CART("/trade/submitGroupedCart", "提交购物车", Set.of("receiveCard", "receiveName", "paymentPassword")),
    APPLY_INVOICE("/invoice/simple/applyInvoice", "申请发票", Set.of("buyerTel", "email"));

    private final String value;

    private final String description;

    private final Set<String> fields;

    private static final Map<String, AliyuEncryptFieldEnum> PATH_INDEX = Arrays.stream(values())
                    .collect(Collectors.toMap(AliyuEncryptFieldEnum::getValue, Function.identity()));

    public static Set<String> fieldsOf(String path) {
        AliyuEncryptFieldEnum rule = PATH_INDEX.get(path);
        return Objects.isNull(rule) ? Set.of() : rule.fields;
    }
}
