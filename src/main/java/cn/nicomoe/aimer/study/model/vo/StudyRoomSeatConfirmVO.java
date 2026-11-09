package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatConfirmClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 签到选座确认结果（/mp/study/seat/confirm）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomSeatConfirmClientRespDTO.class)
public class StudyRoomSeatConfirmVO {

    /**
     * 签到成功后的提示文案（签到成功页直接展示）
     */
    private String studyTipText;

}
