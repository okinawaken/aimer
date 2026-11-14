package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 联系人 / 收件人列表响应（{@code GET /user/getDis}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisListRespDTO {

    /** 联系人 / 收件人列表 */
    private List<UserDisRespDTO> dataList;

    /** 结束行号 */
    private Long endRow;

    /** 是否还有下一页 */
    private Boolean nextPage;

    /** 当前页码 */
    private Long page;

    /** 每页数量 */
    private Long pageSize;

    /** 起始行号 */
    private Long startRow;

    /** 总页数 */
    private Long totalPage;

    /** 总行数（地址管理页据此判断是否达到上限 20） */
    private Long totalRow;
}
