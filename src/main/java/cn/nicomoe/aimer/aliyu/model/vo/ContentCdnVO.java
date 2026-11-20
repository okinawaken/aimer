package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ContentCdnRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * CDN 配置（{@code GET /cms/getCdn}）。
 */
@Data
@AutoMapper(target = ContentCdnRespDTO.class)
public class ContentCdnVO {

    /**
     * 是否启用 CDN 静态资源
     */
    private Boolean isEnable;

    /**
     * 静态资源地址
     */
    private String url;
}
