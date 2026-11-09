package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVenueClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 抖音核销门店
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomDouYinVenueClientRespDTO.class)
public class StudyRoomDouYinVenueVO {

    /**
     * 门店ID
     */
    private Long id;

    /**
     * 抖音 POI ID
     */
    private String poiId;

    /**
     * 抖音 POI 名称
     */
    private String poiName;

    /**
     * 门店名称
     */
    private String venueName;
}
