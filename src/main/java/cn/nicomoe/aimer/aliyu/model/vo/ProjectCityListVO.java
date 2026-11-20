package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ProjectCityListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 项目所在城市（{@code GET /region/getProjectCityList}）。
 */
@Data
@AutoMapper(target = ProjectCityListRespDTO.class)
public class ProjectCityListVO {

    /**
     * 地区 ID（筛选项 value）
     */
    private String areaId;

    /**
     * 地区名称（筛选项文案）
     */
    private String areaName;

    /**
     * 地区类型（行政区划级别）
     */
    private Long areaType;

    /**
     * 英文名称
     */
    private String enName;

    /**
     * 父级地区 ID
     */
    private String parentId;

    /**
     * 下级城市列表
     */
    private List<ProjectCityListVO> cityList;
}
