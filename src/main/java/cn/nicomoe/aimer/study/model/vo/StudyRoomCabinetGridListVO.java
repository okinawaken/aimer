package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetGridListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 柜体格口列表（/mp/cabinetGrip/v2/cabinet/grid/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetGridListClientRespDTO.class)
public class StudyRoomCabinetGridListVO {

    /**
     * 格口列表
     */
    private List<StudyRoomCabinetGridVO> list;

}
