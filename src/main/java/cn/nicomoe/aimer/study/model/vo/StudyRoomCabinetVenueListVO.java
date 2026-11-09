package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetVenueListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 门店储物柜列表（/mp/cabinetGrip/v2/venue/cabinet/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetVenueListClientRespDTO.class)
public class StudyRoomCabinetVenueListVO {

    /**
     * 柜子列表
     */
    private List<StudyRoomCabinetVenueVO> list;

}
