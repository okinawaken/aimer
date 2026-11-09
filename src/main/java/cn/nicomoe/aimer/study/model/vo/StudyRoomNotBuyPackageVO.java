package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomNotBuyPackageClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 未购买套餐推荐
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomNotBuyPackageClientRespDTO.class)
public class StudyRoomNotBuyPackageVO {

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 套餐类型
     */
    private Integer packageType;

    /**
     * 套餐简介
     */
    private String packageIntroDesc;
}
