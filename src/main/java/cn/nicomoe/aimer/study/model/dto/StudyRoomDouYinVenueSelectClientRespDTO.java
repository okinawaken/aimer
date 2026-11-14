package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 抖音核销门店列表（/mp/douYin/venue/select） */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomDouYinVenueSelectClientRespDTO {

    /** 门店列表 */
    private List<StudyRoomDouYinVenueClientRespDTO> venueList;
}
