package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 基础设置（{@code GET /cms/basicSetting/getBasicSetting}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicSettingRespDTO {

    /**
     * 主题色（写入 siteSkin）
     */
    private String color;

    /**
     * 设置 ID
     */
    private String id;

    /**
     * 商品店铺标签
     */
    private JsonNode itemShopTag;

    /**
     * 站点名称（写入 siteName）
     */
    private String name;
}
