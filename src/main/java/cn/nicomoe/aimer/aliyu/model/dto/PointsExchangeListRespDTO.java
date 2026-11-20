package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 积分兑换订单列表响应（{@code POST /aliyuMember/order/pointsExchange/list}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsExchangeListRespDTO {

    /**
     * 兑换订单列表
     */
    private java.util.List<PointsExchangeDetailRespDTO> dataList;

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