package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 会员券列表响应（{@code POST /aliyuMember/memberCoupon/list}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberCouponListRespDTO {

    /**
     * 会员券列表
     */
    private java.util.List<MemberCouponRespDTO> dataList;

    /**
     * 结束行号
     */
    private Long endRow;

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
     * 总记录数（用于判断是否还有下一页）
     */
    private Long totalRow;
}