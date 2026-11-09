package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAreaClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 区域（/mp/reserve/area/select）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomAreaClientRespDTO.class)
public class StudyRoomAreaVO {

    /**
     * 区域ID
     */
    private Long id;

    /**
     * 区域名称
     */
    private String areaName;
}
