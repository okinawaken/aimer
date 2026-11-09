package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 签到记录列表（/mp/user/userSign/list、/list/more）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomUserSignListClientRespDTO {

    /**
     * 签到记录
     */
    private List<StudyRoomUserSignClientRespDTO> list;

}
