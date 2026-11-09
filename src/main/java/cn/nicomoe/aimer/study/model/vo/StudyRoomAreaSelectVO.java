package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAreaSelectClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 区域筛选列表（/mp/reserve/area/select）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomAreaSelectClientRespDTO.class)
public class StudyRoomAreaSelectVO {

    /**
     * 区域列表
     */
    private List<StudyRoomAreaVO> areaList;
}
