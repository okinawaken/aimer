package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomGuardListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 门店门禁区域列表（/mp/guard/select）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomGuardListClientRespDTO.class)
public class StudyRoomGuardListVO {

    /**
     * 门禁区域列表
     */
    private List<StudyRoomGuardVO> guardList;

}
