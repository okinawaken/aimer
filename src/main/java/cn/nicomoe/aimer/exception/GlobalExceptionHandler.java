package cn.nicomoe.aimer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.core.view.R;

/**
 * 业务接口全局异常处理器。
 * <p>
 * 仅作用于本项目包（{@code cn.nicomoe.aimer}），将 Erupt 的业务运行时异常
 * {@link EruptWebApiRuntimeException} 及其它未知异常统一转换为 {@link R} 响应，
 * 不覆盖 Erupt 自有 {@code xyz.erupt} 包下的异常处理。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Slf4j
@RestControllerAdvice(basePackages = "cn.nicomoe.aimer")
public class GlobalExceptionHandler {

    /**
     * 处理业务运行时异常。
     *
     * @param e 业务运行时异常
     * @return 统一响应结果
     */
    @ExceptionHandler(value = EruptWebApiRuntimeException.class)
    public R<Void> handleBusinessException(EruptWebApiRuntimeException e) {
        log.warn("业务异常：{}", e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * 处理未知异常。
     *
     * @param e 未知异常
     * @return 统一响应结果
     */
    @ExceptionHandler(value = Exception.class)
    public R<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return R.error("系统异常，请联系管理员");
    }
}
