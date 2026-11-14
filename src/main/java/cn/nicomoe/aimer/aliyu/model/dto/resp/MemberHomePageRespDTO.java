package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberHomePageRespDTO {
    private java.util.List<CartInfo> cartInfoList;
    private Long level;
    private String levelCode;
    private String levelName;
    private Long memberFlag;
    private String nickname;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CartInfo {
        private String cardBg;
        private String cardIcon;
        private String cardName;
        private String cardType;
        private String points;
    }
}
