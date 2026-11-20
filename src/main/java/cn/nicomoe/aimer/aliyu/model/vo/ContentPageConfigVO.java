package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ContentPageConfigRespDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 搭建页面配置（{@code GET /pageConfig/getPageConfig}）。 响应的 {@code content} 为 JSON 字符串，解析后结构见 {@link CmsPageConfigVO}。
 */
@Data
@AutoMapper(target = ContentPageConfigRespDTO.class)
public class ContentPageConfigVO {

    /**
     * 页面搭建配置 JSON 字符串（需二次 {@code JSON.parse}）
     */
    private String content;

    /**
     * {@link #content} 解析后的搭建配置结构。 <p> 仅用于调用方反序列化 {@link #content} 时复用，非响应直接字段。
     */
    @Data
    public static class CmsPageConfigVO {

        /**
         * 状态码：0 有效
         */
        private Long code;

        /**
         * 楼层模块列表，元素为 {@code {type, config, data, ...}}；配置与数据结构随模块类型变化。
         */
        private List<JsonNode> moduleList;

        /**
         * 页面级配置
         */
        private CmsPageConfigDetailVO pageConfig;
    }

    /**
     * 页面级配置。
     */
    @Data
    public static class CmsPageConfigDetailVO {

        /**
         * 分享配置
         */
        private CmsShareConfigVO shareConfig;
    }

    /**
     * 分享配置。
     */
    @Data
    public static class CmsShareConfigVO {

        /**
         * 默认分享图，以 {@code //} 开头时需补全 {@code https:}
         */
        private String shareImage;

        /**
         * 默认分享标题
         */
        private String shareTitle;
    }
}
