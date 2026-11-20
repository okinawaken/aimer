package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 会员券列表请求（{@code POST /aliyuMember/memberCoupon/list}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsMallMemberCouponListReqDTO {

    /**
     * 页码，从 1 开始
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 券状态（按 tab 传入）
     */
    private String couponStatus;

    /**
     * 纬度（定位授权时携带）
     */
    private String latitude;

    /**
     * 经度（定位授权时携带）
     */
    private String longitude;
}
