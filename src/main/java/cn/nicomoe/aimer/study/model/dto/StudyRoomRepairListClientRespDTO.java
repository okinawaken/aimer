package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 报修列表（/mp/userRepair/list）
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomRepairListClientRespDTO {

    /**
     * 报修单列表
     */
    private List<StudyRoomRepairClientRespDTO> list;
}
