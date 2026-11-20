package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ProjectTypeListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 项目分类（{@code GET /proType/getProjectTypeList}）。
 */
@Data
@AutoMapper(target = ProjectTypeListRespDTO.class)
public class ProjectTypeListVO {

    /**
     * 子分类
     */
    private List<ProjectTypeListVO> children;

    /**
     * 移动端分类图片
     */
    private String imgMobile;

    /**
     * Web 端分类图片
     */
    private String imgWeb;

    /**
     * 父级分类 ID
     */
    private String parentId;

    /**
     * 分类 ID（筛选项 value）
     */
    private String projectTypeId;

    /**
     * 分类名称（筛选项文案）
     */
    private String projectTypeName;

    /**
     * 在售项目数
     */
    private Long saleProjectCount;
}
