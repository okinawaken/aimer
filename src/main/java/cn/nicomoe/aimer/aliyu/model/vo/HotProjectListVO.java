package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.HotProjectListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 热门项目（{@code GET /pro/hotProjectVos}）。
 */
@Data
@AutoMapper(target = HotProjectListRespDTO.class)
public class HotProjectListVO {

    /**
     * 热门项目列表（元素结构与项目列表卡片一致）
     */
    private List<ProjectListVO.DataObjVO> dataList;

    /**
     * 当前页最后一行下标
     */
    private Long endRow;

    /**
     * 是否还有下一页
     */
    private Boolean nextPage;

    /**
     * 当前页码
     */
    private Long page;

    /**
     * 每页条数
     */
    private Long pageSize;

    /**
     * 当前页起始行下标
     */
    private Long startRow;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总条数
     */
    private Long totalRow;
}
