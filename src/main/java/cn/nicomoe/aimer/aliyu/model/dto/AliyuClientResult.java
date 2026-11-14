package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 阿里鱼小程序统一响应体（maitix 结构 {@code {code, msg, msgKey, data}}）： <pre> {"code":"200","msg":"","msgKey":"","data":{...}} </pre> <p> {@code code} 是字符串业务码，{@code "200"} 为成功；未知字段已忽略，统一按 {@code code} 判断。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuClientResult<T> {

    /** 业务码，{@code "200"} 表示成功 */
    private String code;

    /** 提示文案，失败时展示给用户 */
    private String msg;

    /** 文案 i18n key（部分接口返回） */
    private String msgKey;

    /** 业务数据 */
    private T data;
}
