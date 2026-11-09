package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 报修列表（/mp/userRepair/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomRepairListClientRespDTO.class)
public class StudyRoomRepairListVO {

    /**
     * 报修单列表
     */
    private List<StudyRoomRepairVO> list;
}
