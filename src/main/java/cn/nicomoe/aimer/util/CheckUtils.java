package cn.nicomoe.aimer.util;

import cn.hutool.core.collection.CollUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import java.util.Collection;
import java.util.Objects;

/** 参数校验工具：不通过时统一抛 {@link EruptWebApiRuntimeException}，由全局异常处理器转成友好响应。 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CheckUtils {

    /** 断言对象不为 {@code null}。 参数 msg：校验失败时的提示信息 */
    public static void throwIfNull(Object obj, String msg) {
        if (Objects.isNull(obj)) {
            throw new EruptWebApiRuntimeException(msg);
        }
    }

    /** 断言集合不为空。 参数 msg：校验失败时的提示信息 */
    public static void throwIfEmpty(Collection<?> collection, String msg) {
        if (CollUtil.isEmpty(collection)) {
            throw new EruptWebApiRuntimeException(msg);
        }
    }

    /** 断言条件为 {@code false}。 参数 msg：条件为 {@code true} 时的提示信息 */
    public static void throwIf(boolean condition, String msg) {
        if (condition) {
            throw new EruptWebApiRuntimeException(msg);
        }
    }
}
