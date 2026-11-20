package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 区域提示
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomAreaTipClientRespDTO {

    /**
     * 0 禁止 / 1 允许
     */
    private Integer allowType;

    /**
     * 提示内容
     */
    private String content;
}
