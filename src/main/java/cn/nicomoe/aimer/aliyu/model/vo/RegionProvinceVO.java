package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.RegionProvinceRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 按省查地区（{@code GET /region/getRegionByProvinceId}）。
 */
@Data
@AutoMapper(target = RegionProvinceRespDTO.class)
public class RegionProvinceVO {

    /**
     * 当前省份
     */
    private RegionVO region;

    /**
     * 城市列表
     */
    private List<CityVO> cityList;

    /**
     * 行政区划节点（省份、城市共用）。
     */
    @Data
    public static class RegionVO {

        /**
         * 区划 ID
         */
        private String id;

        /**
         * 区划名称
         */
        private String n;
    }

    /**
     * 城市。
     */
    @Data
    public static class CityVO {

        /**
         * 城市区划
         */
        private RegionVO region;

        /**
         * 城市 ID
         */
        private String id;

        /**
         * 城市名称
         */
        private String n;

        /**
         * 区县映射：key 为区县 ID，value 为区县名称
         */
        private Map<String, String> areaMap;
    }
}
