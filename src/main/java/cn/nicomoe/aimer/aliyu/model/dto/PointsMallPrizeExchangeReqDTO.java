package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 奖品兑换请求（{@code POST /aliyuMember/pointsMall/prize/exchange}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsMallPrizeExchangeReqDTO {

    /** 奖品 ID */
    private Long pointsPrizeId;

    /** 兑换数量 */
    private Integer amount;

    /** 会员卡 ID */
    private String cardId;

    /** 门店 ID */
    private String storeId;
}
