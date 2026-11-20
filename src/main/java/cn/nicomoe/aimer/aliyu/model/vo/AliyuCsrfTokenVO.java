package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * CSRF token 响应（{@code GET /getCsrfToken}）。
 */
@Data
@AutoMapper(target = AliyuCsrfTokenRespDTO.class)
public class AliyuCsrfTokenVO {

    /**
     * CSRF token 明文
     */
    @JsonProperty(value = "xsrfToken")
    private String csrfToken;

    /**
     * 有效期（秒），真机实测为 60
     */
    private Long expireTime;
}
