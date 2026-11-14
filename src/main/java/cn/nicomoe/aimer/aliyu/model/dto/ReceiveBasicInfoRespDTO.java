package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 收件基础信息（{@code GET /region/loadReceiveBasicInfo}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveBasicInfoRespDTO {

    /** 国家列表 */
    private List<Country> countryList;

    /** 国家映射 */
    private Object countryMap;

    /** 字典列表 */
    private Object dictionaryList;

    /** 省份列表 */
    private List<Province> provinceList;

    /** 国家。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Country {

        /** 国家 ID（{@code countryInfo.i}，与省份查询入参 countryId 比较） */
        private String i;

        /** 国家名称 */
        private String n;

        /** 一级区划启用标记（非空时展示“现居住地”选择器） */
        private String regionFirst;

        /** 二级区划启用标记 */
        private String regionSecond;

        /** 三级区划启用标记 */
        private String regionThird;
    }

    /** 省份。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Province {

        /** 其它属性 */
        private Object at;

        /** 省份 ID（选择器 value） */
        private String id;

        /** 省份名称（选择器 label） */
        private String n;

        /** 排序值 */
        private Object order;
    }
}