package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomPackageVenueClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 套餐可用门店
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomPackageVenueClientRespDTO.class)
public class StudyRoomPackageVenueVO {

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
    private List<StudyRoomPackageAreaVO> venueAreaList;
}
