package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetGridClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 柜体格口
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetGridClientRespDTO.class)
public class StudyRoomCabinetGridVO {

    /**
     * 格口ID
     */
    private Integer gridId;

    /**
     * 格口编号
     */
    private String gridCode;

    /**
     * 格口状态
     */
    private Integer gridStatus;

}
