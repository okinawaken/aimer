package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

/** 按省查地区（{@code GET /region/getRegionByProvinceId}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionProvinceRespDTO {

    /** 当前省份 */
    private Region region;

    /** 城市列表 */
    private List<City> cityList;

    /** 行政区划节点（省份、城市共用）。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Region {

        /** 区划 ID */
        private String id;

        /** 区划名称 */
        private String n;
    }

    /** 城市。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class City {

        /** 城市区划 */
        private Region region;

        /** 城市 ID */
        private String id;

        /** 城市名称 */
        private String n;

        /** 区县映射：key 为区县 ID，value 为区县名称 */
        private Map<String, String> areaMap;
    }
}
