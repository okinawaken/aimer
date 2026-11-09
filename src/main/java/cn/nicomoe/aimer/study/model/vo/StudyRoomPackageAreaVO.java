package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomPackageAreaClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 套餐可用区域
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomPackageAreaClientRespDTO.class)
public class StudyRoomPackageAreaVO {

    /**
     * 区域ID
     */
    private Long id;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域说明
     */
    private String areaDesc;

    /**
     * 小时单价（元）
     */
    private BigDecimal priceHourYuan;

    /**
     * 日单价（元）
     */
    private BigDecimal priceDayYuan;

    /**
     * 月单价（元）
     */
    private BigDecimal priceMonthYuan;
}
