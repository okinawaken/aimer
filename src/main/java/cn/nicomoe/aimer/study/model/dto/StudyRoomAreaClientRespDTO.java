package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 区域（/mp/reserve/area/select）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomAreaClientRespDTO {

    /**
     * 区域ID
     */
    private Long id;

    /**
     * 区域名称
     */
    private String areaName;
}
