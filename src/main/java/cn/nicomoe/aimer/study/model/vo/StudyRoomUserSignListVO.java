package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSignListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 签到记录列表（/mp/user/userSign/list、/list/more）
 */
@Data
@AutoMapper(target = StudyRoomUserSignListClientRespDTO.class)
public class StudyRoomUserSignListVO {

    /**
     * 签到记录
     */
    private List<StudyRoomUserSignVO> list;

}
