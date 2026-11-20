package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.RegionCountryRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 按国家查地区（{@code GET /region/getRegionByCountryId}）。
 */
@Data
@AutoMapper(target = RegionCountryRespDTO.class)
public class RegionCountryVO {

    /**
     * 省份列表
     */
    private List<ProvinceVO> provinceList;

    /**
     * 省份。
     */
    @Data
    public static class ProvinceVO {

        /**
         * 省份 ID
         */
        private String provinceId;

        /**
         * 省份名称
         */
        private String provinceName;
    }
}
