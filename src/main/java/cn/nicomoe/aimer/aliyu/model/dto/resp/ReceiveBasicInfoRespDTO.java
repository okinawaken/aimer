package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveBasicInfoRespDTO {
    private java.util.List<Country> countryList;
    private Object countryMap;
    private Object dictionaryList;
    private java.util.List<Province> provinceList;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Country {
        private String i;
        private String n;
        private String regionFirst;
        private String regionSecond;
        private String regionThird;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Province {
        private Object at;
        private String id;
        private String n;
        private Object order;
    }
}
