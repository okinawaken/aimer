package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 搭建页面配置查询请求（{@code GET /pageConfig/getPageConfig}、{@code /pageConfig/getBackUrl}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentPageConfigReqDTO {

    /** 页面类型，首页传 {@code home}。 */
    private String pageType;
}