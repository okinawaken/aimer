package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.PointsExchangeDetailRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 积分兑换订单详情响应（{@code GET /aliyuMember/order/pointsExchange/detail}）。
 */
@Data
@AutoMapper(target = PointsExchangeDetailRespDTO.class)
public class PointsExchangeDetailVO {

    /**
     * 订单 ID
     */
    private String orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单状态（2/3/4 等）
     */
    private Long orderStatus;

    /**
     * 订单状态描述
     */
    private String orderStatusDesc;

    /**
     * 下单时间
     */
    private String orderTime;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 完成时间
     */
    private String completeTime;

    /**
     * 奖品类型
     */
    private Long prizeType;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品图片
     */
    private String prizePic;

    /**
     * 兑换数量
     */
    private Long amount;

    /**
     * 消耗积分
     */
    private String pointsFee;

    /**
     * 会员卡 ID
     */
    private String cardId;

    /**
     * 素材 ID（壁纸类）
     */
    private Long materialId;

    /**
     * 系列 ID（壁纸类）
     */
    private Long seriesId;

    /**
     * 备注
     */
    private String remark;
}
