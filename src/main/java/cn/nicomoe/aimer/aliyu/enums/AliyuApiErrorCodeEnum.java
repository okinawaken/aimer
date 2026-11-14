package cn.nicomoe.aimer.aliyu.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 阿里鱼小程序上游错误码。
 * <p>
 * 上游返回的 {@code code} 是字符串业务码，不能直接当作系统错误码使用。非成功业务码的文案原样透传给前端；
 * {@link #UPSTREAM_ERROR} 仅在上游未给出文案时兜底。
 *
 * @author bixiu
 * @since 4.3.0
 */
@Getter
@RequiredArgsConstructor
public enum AliyuApiErrorCodeEnum implements BaseEnum<Integer> {

    /**
     * 绑定凭证校验失败（cookie / 会话已失效）
     */
    CREDENTIAL_INVALID(40201, "阿里鱼小程序凭证已失效，请重新绑定"),

    /**
     * 上游返回了非成功的业务码且未给出文案时的兜底提示
     */
    UPSTREAM_ERROR(40202, "阿里鱼小程序接口调用失败"),

    /**
     * 上游返回为空（网络异常、响应体为空等）
     */
    EMPTY_RESPONSE(40203, "阿里鱼小程序接口返回为空"),

    /**
     * 无登录上下文（未登录，或定时任务等场景未显式传入 userId）
     */
    NOT_LOGIN(40204, "未登录，无法获取阿里鱼凭证"),

    /**
     * 当前用户未绑定阿里鱼凭证束
     */
    CREDENTIAL_MISSING(40205, "未绑定阿里鱼小程序凭证，请先在设置中绑定"),

    /**
     * POST 接口无法获取 CSRF token
     */
    CSRF_UNAVAILABLE(40206, "阿里鱼 CSRF token 获取失败，请检查凭证是否有效");

    /**
     * 枚举值
     */
    private final Integer value;

    /**
     * 枚举描述
     */
    private final String description;
}
