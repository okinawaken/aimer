package cn.nicomoe.aimer.aliyu.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 一次请求要注入的阿里鱼运行态凭证束。
 */
@Data
@AllArgsConstructor
public class AliyuCredentialBO {

    /**
     * 运行态 Cookie（可选，仅由响应 {@code Set-Cookie} 维护），凭据，禁止输出日志
     */
    @ToString.Exclude
    private String cookie;

    /**
     * maitix 会话标识（{@code X-MZ-SESSION}），可为空，凭据，禁止输出日志
     */
    @ToString.Exclude
    private String session;

    /**
     * CSRF token（{@code X-XSRF-TOKEN}），凭据，禁止输出日志
     */
    @ToString.Exclude
    private String csrf;

    /**
     * 操作者 ID（{@code Mzoperateid}），可为空
     */
    private String operateId;
}
