package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueSelectClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 门店下拉列表（/mp/venue/select、/mp/reserve/venue/select、/mp/receipt/venue/select）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomVenueSelectClientRespDTO.class)
public class StudyRoomVenueSelectVO {

    /**
     * 门店列表
     */
    private List<StudyRoomVenueSimpleVO> venueList;
}
