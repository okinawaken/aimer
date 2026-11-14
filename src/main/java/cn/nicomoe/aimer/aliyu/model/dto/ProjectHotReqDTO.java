package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 热门项目查询请求（{@code GET /pro/hotProjectVos}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectHotReqDTO {

    /** 页面类型，项目详情页传 1。 */
    private Integer pageType;

    /** 是否从响应中解析站点。 */
    private Boolean fromRespSite;
}
