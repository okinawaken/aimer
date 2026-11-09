package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 通用「ID + 名称」选项
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomIdNameClientRespDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;
}
