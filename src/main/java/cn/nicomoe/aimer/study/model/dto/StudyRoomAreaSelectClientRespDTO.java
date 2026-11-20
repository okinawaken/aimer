package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 区域筛选列表（/mp/reserve/area/select）
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomAreaSelectClientRespDTO {

    /**
     * 区域列表
     */
    private List<StudyRoomAreaClientRespDTO> areaList;
}
