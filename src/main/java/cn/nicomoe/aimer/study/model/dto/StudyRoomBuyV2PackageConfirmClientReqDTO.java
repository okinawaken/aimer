package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/** 确认下单 v2（{@code /mp/buy/v2/package/confirm}）。 */
@Data
public class StudyRoomBuyV2PackageConfirmClientReqDTO {

    /** 套餐ID（必填） */
    private Long packageId;

    /** 门店ID（必填） */
    private Integer venueId;

    /** 当前经度 */
    private Double currentLng;

    /** 当前纬度 */
    private Double currentLat;

    /** 购买数量（必填） */
    private Integer purchaseNum;

    /** 座位区域ID（可选，取自套餐标签接口的 seatAreaList） */
    private Long seatAreaId;

    /** 套餐类型（可选，取自套餐详情的 packageType） */
    private Integer packageType;

}
