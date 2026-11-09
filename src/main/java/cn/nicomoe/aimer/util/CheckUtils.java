package cn.nicomoe.aimer.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import java.util.Collection;
import java.util.Objects;

/**
 * 参数校验工具类。
 * <p>
 * 校验不通过时统一抛出 {@link EruptWebApiRuntimeException}，由全局异常处理器转换为友好响应。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CheckUtils {

    /**
     * 断言对象不为 {@code null}。
     *
     * @param obj 待校验对象
     * @param msg 校验失败时的提示信息
     */
    public static void throwIfNull(Object obj, String msg) {
        if (Objects.isNull(obj)) {
            throw new EruptWebApiRuntimeException(msg);
        }
    }

    /**
     * 断言集合不为空。
     *
     * @param collection 待校验集合
     * @param msg        校验失败时的提示信息
     */
    public static void throwIfEmpty(Collection<?> collection, String msg) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw new EruptWebApiRuntimeException(msg);
        }
    }

    /**
     * 断言条件为 {@code false}。
     *
     * @param condition 待校验条件
     * @param msg       条件为 {@code true} 时的提示信息
     */
    public static void throwIf(boolean condition, String msg) {
        if (condition) {
            throw new EruptWebApiRuntimeException(msg);
        }
    }
}
