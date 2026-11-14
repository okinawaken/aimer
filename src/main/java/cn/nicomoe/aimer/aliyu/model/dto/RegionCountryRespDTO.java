package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 按国家查地区（{@code GET /region/getRegionByCountryId}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionCountryRespDTO {

    /** 省份列表 */
    private List<Province> provinceList;

    /** 省份。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Province {

        /** 省份 ID */
        private String provinceId;

        /** 省份名称 */
        private String provinceName;
    }
}
