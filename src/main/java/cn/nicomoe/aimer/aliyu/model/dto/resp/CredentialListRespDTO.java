package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialListRespDTO {
    private Long allowDelete;
    private Boolean canOperate;
    private Object children;
    private Long enableState;
    private String gmtCreate;
    private String gmtModified;
    private String itemExt1;
    private Object itemExt2;
    private Object itemExt3;
    private String itemId;
    private String itemValue;
    private Object offLine;
    private Object parentItemId;
    private String sysDictionaryKey;


}
