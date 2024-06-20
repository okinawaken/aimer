package org.dromara.aimer.common.exception;


/**
 * 用户信息异常类
 *
 * @author bixiu
 */
public class UserException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super(code, args);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Exception e) {
        super(e);
    }
}
