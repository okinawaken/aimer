package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 栏目信息（{@code GET /pro/getColumnInfo}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectColumnInfoRespDTO {

    /**
     * 栏目名称
     */
    private String name;

    /**
     * 栏目头图
     */
    private String img;

    /**
     * 栏目下项目列表
     */
    private List<ProjectListRespDTO.DataObj> projectVoList;
}
