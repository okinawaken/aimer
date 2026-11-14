package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * CSRF token 响应（{@code GET /getCsrfToken}）。
 *
 * @author bixiu
 * @since 4.3.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuCsrfTokenRespDTO {

    /**
     * CSRF token 明文
     */
    @JsonProperty("xsrfToken")
    private String csrfToken;

    /**
     * 有效期（秒），真机实测为 60
     */
    private Long expireTime;
}
