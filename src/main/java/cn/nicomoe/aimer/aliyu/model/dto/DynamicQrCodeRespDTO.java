package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 动态会员码响应（{@code GET /aliyupoint/getDynamicQrCode}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DynamicQrCodeRespDTO {

    /**
     * 动态码内容（用于生成二维码）
     */
    private String code;

    /**
     * 动态码失效时间（毫秒时间戳）
     */
    private Long validTime;
}