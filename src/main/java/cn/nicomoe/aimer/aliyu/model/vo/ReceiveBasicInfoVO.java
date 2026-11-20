package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ReceiveBasicInfoRespDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 收件基础信息（{@code GET /region/loadReceiveBasicInfo}）。
 */
@Data
@AutoMapper(target = ReceiveBasicInfoRespDTO.class)
public class ReceiveBasicInfoVO {

    /**
     * 国家列表
     */
    private List<CountryVO> countryList;

    /**
     * 国家映射
     */
    private Map<String, JsonNode> countryMap;

    /**
     * 字典列表
     */
    private List<JsonNode> dictionaryList;

    /**
     * 省份列表
     */
    private List<ProvinceVO> provinceList;

    /**
     * 国家。
     */
    @Data
    public static class CountryVO {

        /**
         * 国家 ID（{@code countryInfo.i}，与省份查询入参 countryId 比较）
         */
        private String i;

        /**
         * 国家名称
         */
        private String n;

        /**
         * 一级区划启用标记（非空时展示“现居住地”选择器）
         */
        private String regionFirst;

        /**
         * 二级区划启用标记
         */
        private String regionSecond;

        /**
         * 三级区划启用标记
         */
        private String regionThird;
    }

    /**
     * 省份。
     */
    @Data
    public static class ProvinceVO {

        /**
         * 其它属性
         */
        private JsonNode at;

        /**
         * 省份 ID（选择器 value）
         */
        private String id;

        /**
         * 省份名称（选择器 label）
         */
        private String n;

        /**
         * 排序值
         */
        private Long order;
    }
}
