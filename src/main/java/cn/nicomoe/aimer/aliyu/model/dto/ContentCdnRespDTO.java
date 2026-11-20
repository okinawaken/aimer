package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * CDN 配置（{@code GET /cms/getCdn}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentCdnRespDTO {

    /**
     * 是否启用 CDN 静态资源
     */
    private Boolean isEnable;

    /**
     * 静态资源地址
     */
    private String url;
}
