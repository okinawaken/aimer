package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAreaTipClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 区域提示
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomAreaTipClientRespDTO.class)
public class StudyRoomAreaTipVO {

    /**
     * 0 禁止 / 1 允许
     */
    private Integer allowType;

    /**
     * 提示内容
     */
    private String content;
}
