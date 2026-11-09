package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 未购买套餐推荐
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomNotBuyPackageClientRespDTO {

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
