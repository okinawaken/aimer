package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVenueSelectClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 抖音核销门店列表（/mp/douYin/venue/select）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomDouYinVenueSelectClientRespDTO.class)
public class StudyRoomDouYinVenueSelectVO {

    /**
     * 门店列表
     */
    private List<StudyRoomDouYinVenueVO> venueList;
}
