package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectTypeListRespDTO {
    private Object children;
    private Object imgMobile;
    private Object imgWeb;
    private Object parentId;
    private String projectTypeId;
    private String projectTypeName;
    private Long saleProjectCount;


}
