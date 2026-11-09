package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 我的柜格列表（/mp/cabinetGrip/v2/user/cabinet/grid/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomCabinetUserGridListClientRespDTO {

    /**
     * 我的柜格
     */
    private List<StudyRoomCabinetUserGridClientRespDTO> list;

}
