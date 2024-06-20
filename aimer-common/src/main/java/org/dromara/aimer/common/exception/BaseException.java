package org.dromara.aimer.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.dromara.aimer.common.utils.MessageUtils;

/**
 * 基础异常
 *
 * @author bixiu
 */
public class BaseException extends RuntimeException {

    private String code;

    private Object[] args;

    public BaseException(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Exception e) {
        super(e);
    }

    @Override
    public String getMessage() {
        if (StringUtils.isNotEmpty(code)) {
            return MessageUtils.message(code, args);
        }
        return super.getMessage();
    }
}
