package cn.nicomoe.aimer.aliyu.model.dto;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 阿里鱼用户设置写请求。
 * <p>
 * 承载真机会话凭证束（抓包获取）。<b>整对象覆盖</b>：未传（null）的配置项不会保留原值。
 * 其中 CSRF token 可留空，由请求拦截器在调用 {@code /getCsrfToken} 后自动回填。
 *
 * @author bixiu
 * @since 4.3.0
 */
@Data
@AutoMapper(target = AliyuUserConfigBO.class)
public class AliyuUserSettingReqDTO {

    /**
     * 持久会话 Cookie（JSESSIONID + MZCONSUMERJSESSIONID + MZOPERATEID）
     */
    @Size(max = 4096, message = "cookie 长度不能超过{max}个字符")
    private String cookie;

    /**
     * 操作者 ID（{@code Mzoperateid} 头，base64 形式）
     */
    @Size(max = 128, message = "操作者 ID 长度不能超过{max}个字符")
    private String operateId;

    /**
     * maitix 会话标识（{@code X-MZ-SESSION} 头）
     */
    @Size(max = 128, message = "会话标识长度不能超过{max}个字符")
    private String sessValue;

    /**
     * CSRF token（{@code X-XSRF-TOKEN} 头），留空则自动获取
     */
    @Size(max = 128, message = "CSRF token 长度不能超过{max}个字符")
    private String csrfValue;

    /**
     * 渠道标识（{@code site} 头），默认 {@code mp_weixin}
     */
    @Size(max = 64, message = "渠道标识长度不能超过{max}个字符")
    private String site;

    /**
     * 阿里 baxia 风控头组
     */
    private AliyuUserConfigBO.Baxia baxia;
}
