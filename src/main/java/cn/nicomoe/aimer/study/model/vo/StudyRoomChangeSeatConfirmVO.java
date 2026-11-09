package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomChangeSeatConfirmClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 换座确认结果（/mp/study/change/seat/confirm）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomChangeSeatConfirmClientRespDTO.class)
public class StudyRoomChangeSeatConfirmVO {

    /**
     * 换座成功后的提示文案
     */
    private String studyTipText;

}
