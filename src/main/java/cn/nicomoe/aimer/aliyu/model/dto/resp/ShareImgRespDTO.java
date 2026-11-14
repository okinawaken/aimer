package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareImgRespDTO {
    private String userShareImageUrl;


}
