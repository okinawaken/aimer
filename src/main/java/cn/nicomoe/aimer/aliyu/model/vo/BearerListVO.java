package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.BearerListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 持票人列表响应（{@code GET /user/getBearers}）。
 */
@Data
@AutoMapper(target = BearerListRespDTO.class)
public class BearerListVO {

    /**
     * 持票人列表
     */
    private List<BearerVO> dataList;

    /**
     * 结束行号
     */
    private Long endRow;

    /**
     * 是否还有下一页（{@code false} 表示已加载完）
     */
    private Boolean nextPage;

    /**
     * 当前页码
     */
    private Long page;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 起始行号
     */
    private Long startRow;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总行数
     */
    private Long totalRow;
}
