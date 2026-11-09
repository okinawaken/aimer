package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 区域与座位号
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomVenueAreaSeatClientRespDTO {

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 座位号
     */
    private String seatNumber;

}
