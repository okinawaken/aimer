package cn.nicomoe.aimer.aliyu.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/**
 * 阿里鱼用户级配置，对应 {@code aliyu_user_setting.config_json}。
 * <p>
 * 卖座 maitix 的真实鉴权是<b>会话凭证束</b>而非单一 token（2026-11-15 真机抓包确认）：
 * 持久身份走 {@code cookie}（JSESSIONID / MZCONSUMERJSESSIONID / MZOPERATEID），
 * 会话标识走 {@code X-MZ-SESSION}（响应头回写，TTL 360s），
 * CSRF 走 {@code X-XSRF-TOKEN}（{@code /getCsrfToken} 获取，TTL 60s，POST 必填），
 * 阿里风控走 baxia 头组。
 * </p>
 * <p>
 * 其中 {@code cookie} / {@code X-MZ-SESSION} 与 baxia 的设备指纹（{@code bx-umidtoken}、{@code x-ticid}）
 * 只能来自真机，无法离线生成；{@code epeius} / {@code hecate} / {@code coeus} 属可复算的风控头，
 * 当前实现仍按配置注入（未在 Java 侧复算）。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuUserConfigBO {

    /**
     * 持久会话 Cookie（JSESSIONID + MZCONSUMERJSESSIONID + MZOPERATEID 拼接），凭据，禁止输出日志
     */
    @ToString.Exclude
    private String cookie;

    /**
     * 操作者 ID（{@code Mzoperateid} 头，base64 形式，如 {@code NjE3MDM3MjI}），凭据
     */
    @ToString.Exclude
    private String operateId;

    /**
     * maitix 会话标识（{@code X-MZ-SESSION} 头，TTL 360s，响应头会回写新值），凭据
     */
    @ToString.Exclude
    private String sessValue;

    /**
     * CSRF token（{@code X-XSRF-TOKEN} 头，TTL 60s）。留空时由请求拦截器自动获取并回填
     */
    @ToString.Exclude
    private String csrfValue;

    /**
     * 渠道标识（{@code site} 头），微信小程序固定 {@code mp_weixin}
     */
    private String site;

    /**
     * 阿里 baxia 风控头组，真机 SDK 产出，随会话/设备绑定
     */
    private Baxia baxia;

    /**
     * 阿里 baxia 风控指纹
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Baxia {

        @ToString.Exclude
        private String epeius;

        @ToString.Exclude
        private String baxiaUmid;

        @ToString.Exclude
        private String hecate;

        @ToString.Exclude
        private String xTicid;

        @ToString.Exclude
        private String coeus;

        @ToString.Exclude
        private String baxiaJanus;
    }
}
