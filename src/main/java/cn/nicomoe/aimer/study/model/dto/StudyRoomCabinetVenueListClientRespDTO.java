package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 门店储物柜列表（/mp/cabinetGrip/v2/venue/cabinet/list） */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomCabinetVenueListClientRespDTO {

    /** 柜子列表 */
    private List<StudyRoomCabinetVenueClientRespDTO> list;

}
