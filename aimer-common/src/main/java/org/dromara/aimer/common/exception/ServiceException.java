package org.dromara.aimer.common.exception;

/**
 * 业务异常
 *
 * @author bixiu
 */
public final class ServiceException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ServiceException(String code, Object[] args) {
        super(code, args);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception e) {
        super(e);
    }
}
