package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 柜体格口
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomCabinetGridClientRespDTO {

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
