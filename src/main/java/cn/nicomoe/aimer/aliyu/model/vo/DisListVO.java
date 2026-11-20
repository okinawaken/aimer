package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.DisListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 联系人 / 收件人列表响应（{@code GET /user/getDis}）。
 */
@Data
@AutoMapper(target = DisListRespDTO.class)
public class DisListVO {

    /**
     * 联系人 / 收件人列表
     */
    private List<UserDisVO> dataList;

    /**
     * 结束行号
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
     * 总行数（地址管理页据此判断是否达到上限 20）
     */
    private Long totalRow;
}
