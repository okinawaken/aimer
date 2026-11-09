package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 抖音核销门店
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomDouYinVenueClientRespDTO {

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
