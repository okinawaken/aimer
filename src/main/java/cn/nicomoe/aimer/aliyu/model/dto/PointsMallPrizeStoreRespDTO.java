package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 奖品 / 会员券可用门店列表项（{@code GET /aliyuMember/pointsMall/prize/store}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsMallPrizeStoreRespDTO {

    /** 门店 ID */
    private String storeId;

    /** 门店名称 */
    private String storeName;

    /** 详细地址（展示、复制、导航用） */
    private String detailAddress;

    /** 经纬度坐标，格式 {@code "经度,纬度"} */
    private String coords;
}