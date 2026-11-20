package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 奖品可用门店查询请求（{@code GET /aliyuMember/pointsMall/prize/store}）。 小程序将调用参数去掉 {@code location} 后透传，并补 {@code latitude/longitude} 字符串。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsMallPrizeStoreReqDTO {

    /**
     * 会员卡 ID。
     */
    private String cardId;

    /**
     * 门店 ID。
     */
    private String storeId;

    /**
     * 纬度（字符串）。
     */
    private String latitude;

    /**
     * 经度（字符串）。
     */
    private String longitude;
}