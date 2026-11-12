package cn.nicomoe.aimer.exception;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.core.view.R;

import java.util.Objects;

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
     * 处理参数绑定校验异常（表单 / 查询参数上的 {@code @Valid} 校验失败）。
     *
     * @param e 参数绑定异常
     * @return 统一响应结果
     */
    @ExceptionHandler(value = BindException.class)
    public R<Void> handleBindException(BindException e) {
        return R.error(this.firstErrorMessage(e.getBindingResult()));
    }

    /**
     * 处理请求体参数校验异常（{@code @RequestBody @Valid} 校验失败）。
     *
     * @param e 请求体参数校验异常
     * @return 统一响应结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return R.error(this.firstErrorMessage(e.getBindingResult()));
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

    /**
     * 取第一条校验失败的提示信息。
     *
     * @param bindingResult 校验结果
     * @return 提示信息
     */
    private String firstErrorMessage(BindingResult bindingResult) {
        ObjectError error = CollUtil.getFirst(bindingResult.getAllErrors());
        String message = Objects.isNull(error) ? null : error.getDefaultMessage();
        log.error("参数校验失败：{}", message);
        return Objects.isNull(message) ? "参数校验失败" : message;
    }
}
