package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.BasicSettingRespDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 基础设置（{@code GET /cms/basicSetting/getBasicSetting}）。
 */
@Data
@AutoMapper(target = BasicSettingRespDTO.class)
public class BasicSettingVO {

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
