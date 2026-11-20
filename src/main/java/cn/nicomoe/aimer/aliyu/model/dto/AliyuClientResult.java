package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 阿里鱼小程序统一响应体（maitix 结构 {@code {code, msg, msgKey, data}}）： <pre> {"code":"200","msg":"","msgKey":"","data":{...}} </pre>
 * <p>
 * {@code code} 是字符串业务码，{@code "200"} 为成功；未知字段已忽略，统一按 {@code code} 判断。
 * 部分接口（小程序请求封装开启 {@code withCode}）还会在顶层返回 {@code success/fail/detailMsg}，
 * 此时业务成功与否需同时参考这些字段。
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
     * 业务是否成功（部分接口在顶层返回，如会员校验、活动报名、任务状态检查）
     */
    private Boolean success;

    /**
     * 业务是否失败（部分接口在顶层返回，如会员注销）
     */
    private Boolean fail;

    /**
     * 失败明细文案（部分活动接口在顶层返回）
     */
    private String detailMsg;

    /**
     * 业务数据
     */
    private T data;
}
