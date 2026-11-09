package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 柜体格口列表（/mp/cabinetGrip/v2/cabinet/grid/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomCabinetGridListClientRespDTO {

    /**
     * 格口列表
     */
    private List<StudyRoomCabinetGridClientRespDTO> list;

}
