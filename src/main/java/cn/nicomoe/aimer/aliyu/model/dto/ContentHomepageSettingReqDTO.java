package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 首页配置查询请求（{@code GET /cms/homepage/getHomepageSetting}）。 小程序首页固定传 {@code dataType=1}（正式数据）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentHomepageSettingReqDTO {

    /**
     * 数据类型：1 正式、2 草稿
     */
    private Integer dataType;
}
