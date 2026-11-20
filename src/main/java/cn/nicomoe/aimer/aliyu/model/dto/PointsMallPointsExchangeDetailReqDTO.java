package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 积分兑换订单详情查询请求（{@code GET /aliyuMember/order/pointsExchange/detail}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsMallPointsExchangeDetailReqDTO {

    /**
     * 订单 ID（字符串化）
     */
    private String orderId;
}
