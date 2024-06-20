package org.dromara.aimer.common.exception;


/**
 * 验证码错误异常类
 *
 * @author bixiu
 */
public class CaptchaException extends BaseException {

    private static final long serialVersionUID = 1L;

    public CaptchaException(String code, Object[] args) {
        super(code, args);
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Exception e) {
        super(e);
    }
}
