package org.dromara.aimer.common.exception;

/**
 * Rpc异常
 *
 * @author bixiu
 */
public class RpcException extends BaseException {

    private static final long serialVersionUID = 1L;

    public RpcException(String code, Object[] args) {
        super(code, args);
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(Exception e) {
        super(e);
    }
}
