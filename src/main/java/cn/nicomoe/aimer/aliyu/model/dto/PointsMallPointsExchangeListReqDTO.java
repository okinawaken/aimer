package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 积分兑换订单列表请求（{@code POST /aliyuMember/order/pointsExchange/list}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsMallPointsExchangeListReqDTO {

    /**
     * 页码，从 1 开始
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;
}
