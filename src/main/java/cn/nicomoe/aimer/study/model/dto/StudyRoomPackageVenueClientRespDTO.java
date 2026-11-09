package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 套餐可用门店
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomPackageVenueClientRespDTO {

    /**
     * 门店ID
     */
    private Long id;

    /**
     * 门店名称
     */
    private String venueName;

    /**
     * 门店地址
     */
    private String venueAddress;

    /**
     * 距离（km）
     */
    private Double distance;

    /**
     * 可用区域
     */
    private List<StudyRoomPackageAreaClientRespDTO> venueAreaList;
}
