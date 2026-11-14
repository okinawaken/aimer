package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 阿里鱼小程序统一响应体。
 * <p>
 * 卖座 maitix 业务响应结构（源码解析：{@code s=t.data, l=s.code, p=s.msg, f=s.msgKey}）：
 *
 * <pre>
 * {"code":"200","msg":"","msgKey":"","data":{...}}
 * </pre>
 *
 * {@code code} 为字符串业务码，{@code "200"} 表示成功；部分接口（如 baxia 风控）使用 {@code success} 布尔位，
 * 因 {@link JsonIgnoreProperties} 已忽略未知字段，统一按 {@code code} 判断。
 *
 * @param <T> 业务数据类型
 * @author bixiu
 * @since 4.3.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuClientResult<T> {

    /**
     * 业务码，{@code "200"} 表示成功
     */
    private String code;

    /**
     * 提示文案，失败时展示给用户
     */
    private String msg;

    /**
     * 文案 i18n key（部分接口返回）
     */
    private String msgKey;

    /**
     * 业务数据
     */
    private T data;
}
