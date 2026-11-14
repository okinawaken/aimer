package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 积分商城当前门店查询请求（{@code GET /aliyuMember/pointsMall/currentStore}）。 小程序仅在有值时传字段；{@code latitude/longitude} 为字符串化的定位。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsMallCurrentStoreReqDTO {

    /** 会员卡 ID。 */
    private String cardId;

    /** 纬度（字符串）。 */
    private String latitude;

    /** 经度（字符串）。 */
    private String longitude;

    /** 已选门店 ID。 */
    private String selectedStoreId;
}