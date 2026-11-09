package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomPackageItemClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 套餐列表项（/mp/buy/v2/package/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomPackageItemClientRespDTO.class)
public class StudyRoomPackageItemVO {

    /**
     * 套餐ID
     */
    private Long id;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 套餐类型
     */
    private Integer packageType;

    /**
     * 套餐类型名称
     */
    private String packageTypeStr;

    /**
     * 套餐说明
     */
    private String packageDesc;

    /**
     * 套餐图片
     */
    private String packageImgPath;

    /**
     * 原价（元）
     */
    private BigDecimal originalPriceYuan;

    /**
     * 实际售价（元）
     */
    private BigDecimal packageAccountPriceYuan;

    /**
     * 角标文案
     */
    private String specialTag;
}
