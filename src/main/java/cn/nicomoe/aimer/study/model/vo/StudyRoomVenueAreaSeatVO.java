package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueAreaSeatClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 区域与座位号
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomVenueAreaSeatClientRespDTO.class)
public class StudyRoomVenueAreaSeatVO {

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 座位号
     */
    private String seatNumber;

}
