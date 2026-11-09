package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomIdNameClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 通用「ID + 名称」选项
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomIdNameClientRespDTO.class)
public class StudyRoomIdNameVO {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;
}
