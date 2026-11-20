package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.PointsMallCurrentStoreRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 积分商城当前门店列表项（{@code GET /aliyuMember/pointsMall/currentStore}）。
 */
@Data
@AutoMapper(target = PointsMallCurrentStoreRespDTO.class)
public class PointsMallCurrentStoreVO {

    /**
     * 门店 ID（mock 为字符串，选中后以 String 形式缓存）
     */
    private String storeId;

    /**
     * 门店编码（如 {@code HZ-YT-001}，选中门店时随 storeId/storeName 一起缓存）
     */
    private String storeCode;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 详细地址（门店卡片展示、复制、导航用）
     */
    private String detailAddress;

    /**
     * 经纬度坐标，格式 {@code "经度,纬度"}（定位与导航时 split(",") 解析）
     */
    private String coords;

    /**
     * 是否默认门店（仅 mock 中出现，前端选中逻辑按 storeId 比较）
     */
    private Boolean isDefault;
}
