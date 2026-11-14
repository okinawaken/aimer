package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/** 微信支付下单（{@code /mp/buy/package/payOrder}）。 */
@Data
public class StudyRoomBuyPayOrderClientReqDTO {

    /** 用户订单ID（必填，取自确认下单返回的 userOrderId） */
    private Long userOrderId;

}
