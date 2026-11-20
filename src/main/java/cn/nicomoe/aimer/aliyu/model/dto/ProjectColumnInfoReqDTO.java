package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 栏目信息查询请求（{@code GET /pro/getColumnInfo}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectColumnInfoReqDTO {

    /**
     * 数据类型：1=正式，2=草稿。
     */
    private Integer dataType;

    /**
     * 栏目 ID。
     */
    private Integer columnId;
}
