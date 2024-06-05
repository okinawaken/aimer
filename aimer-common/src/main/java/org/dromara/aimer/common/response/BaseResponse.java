package org.dromara.aimer.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.aimer.common.constants.HttpStatus;

import java.io.Serializable;

/**
 * 响应信息主体
 */
@Data
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    public static <T> BaseResponse<T> ok() {
        return restResult(null, HttpStatus.SUCCESS, "操作成功");
    }

    public static <T> BaseResponse<T> ok(T data) {
        return restResult(data, HttpStatus.SUCCESS, "操作成功");
    }

    public static <T> BaseResponse<T> ok(String msg) {
        return restResult(null, HttpStatus.SUCCESS, msg);
    }

    public static <T> BaseResponse<T> ok(String msg, T data) {
        return restResult(data, HttpStatus.SUCCESS, msg);
    }

    public static <T> BaseResponse<T> fail() {
        return restResult(null, HttpStatus.ERROR, "操作失败");
    }

    public static <T> BaseResponse<T> fail(String msg) {
        return restResult(null, HttpStatus.ERROR, msg);
    }

    public static <T> BaseResponse<T> fail(T data) {
        return restResult(data, HttpStatus.ERROR, "操作失败");
    }

    public static <T> BaseResponse<T> fail(String msg, T data) {
        return restResult(data, HttpStatus.ERROR, msg);
    }

    public static <T> BaseResponse<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> BaseResponse<T> warn(String msg) {
        return restResult(null, HttpStatus.WARN, msg);
    }

    public static <T> BaseResponse<T> warn(String msg, T data) {
        return restResult(data, HttpStatus.WARN, msg);
    }

    private static <T> BaseResponse<T> restResult(T data, int code, String msg) {
        BaseResponse<T> r = new BaseResponse<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static <T> Boolean isError(BaseResponse<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(BaseResponse<T> ret) {
        return HttpStatus.SUCCESS == ret.getCode();
    }
}
