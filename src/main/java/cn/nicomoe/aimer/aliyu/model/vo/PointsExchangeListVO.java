package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.PointsExchangeListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 积分兑换订单列表响应（{@code POST /aliyuMember/order/pointsExchange/list}）。
 */
@Data
@AutoMapper(target = PointsExchangeListRespDTO.class)
public class PointsExchangeListVO {

    /**
     * 兑换订单列表
     */
    private java.util.List<PointsExchangeDetailVO> dataList;

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
