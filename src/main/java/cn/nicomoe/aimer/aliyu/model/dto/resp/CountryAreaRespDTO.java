package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryAreaRespDTO {
    private java.util.List<Object> hot;
    private java.util.List<Normal> normal;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Normal {
        private String countryAbbrCode;
        private String countryAreaCode;
        private String countryCnName;
        private String countryEnName;
        private String countryId;
        private String countryNameKey;
        private String firstPinyin;
        private Long registerType;
    }
}
