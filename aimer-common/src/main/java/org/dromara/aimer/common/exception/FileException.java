package org.dromara.aimer.common.exception;


/**
 * 文件信息异常类
 *
 * @author bixiu
 */
public class FileException extends BaseException {

    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super(code, args);
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(Exception e) {
        super(e);
    }
}
