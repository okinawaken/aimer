package org.dromara.aimer.console.configuration.exception;


import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.common.exception.BaseException;
import org.dromara.aimer.common.response.BaseResponse;
import org.dromara.aimer.common.utils.StreamUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * 异常处理器
 *
 * @author bixiu
 */
@Slf4j
@RestControllerAdvice
public class ConsoleExceptionHandler {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                  HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return BaseResponse.fail(e.getMessage());
    }

    /**
     * 请求路径中缺少必需的路径变量
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public BaseResponse<Void> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求路径中缺少必需的路径变量'{}',发生系统异常.", requestURI);
        return BaseResponse.fail(String.format("请求路径中缺少必需的路径变量[%s]", e.getVariableName()));
    }

    /**
     * 请求参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求参数类型不匹配'{}',发生系统异常.", requestURI);
        return BaseResponse.fail(String.format("请求参数类型不匹配，参数[%s]要求类型为：'%s'，但输入值为：'%s'", e.getName(), e.getRequiredType().getName(), e.getValue()));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public BaseResponse<Void> handleBindException(BindException e) {
        log.error(e.getMessage());
        String message = StreamUtils.join(e.getAllErrors(), DefaultMessageSourceResolvable::getDefaultMessage, ", ");
        return BaseResponse.fail(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<Void> constraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage());
        String message = StreamUtils.join(e.getConstraintViolations(), ConstraintViolation::getMessage, ", ");
        return BaseResponse.fail(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return BaseResponse.fail(message);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BaseException.class)
    public BaseResponse<Void> handleServiceException(BaseException e, HttpServletRequest request) {
        log.error(e.getMessage());
        return BaseResponse.fail(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return BaseResponse.fail(e.getMessage());
    }
}
