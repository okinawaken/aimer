package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ProjectColumnInfoRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 栏目信息（{@code GET /pro/getColumnInfo}）。
 */
@Data
@AutoMapper(target = ProjectColumnInfoRespDTO.class)
public class ProjectColumnInfoVO {

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
    private List<ProjectListVO.DataObjVO> projectVoList;
}
