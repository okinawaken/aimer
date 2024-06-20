package org.dromara.aimer.common.response;

import lombok.Data;
import org.dromara.aimer.common.constants.HttpStatusConstant;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author bixiu
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    public static <T> BaseResponse<T> ok() {
        return restResult(null, HttpStatusConstant.SUCCESS, "操作成功");
    }

    public static <T> BaseResponse<T> ok(T data) {
        return restResult(data, HttpStatusConstant.SUCCESS, "操作成功");
    }

    public static <T> BaseResponse<T> fail() {
        return restResult(null, HttpStatusConstant.ERROR, "操作失败");
    }

    public static <T> BaseResponse<T> fail(String msg) {
        return restResult(null, HttpStatusConstant.ERROR, msg);
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
        return HttpStatusConstant.SUCCESS == ret.getCode();
    }
}
