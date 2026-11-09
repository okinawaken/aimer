package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetUserGridListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 我的柜格列表（/mp/cabinetGrip/v2/user/cabinet/grid/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetUserGridListClientRespDTO.class)
public class StudyRoomCabinetUserGridListVO {

    /**
     * 我的柜格
     */
    private List<StudyRoomCabinetUserGridVO> list;

}
