package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomRankClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 学习排行榜（/mp/study/rank/index）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomRankClientRespDTO.class)
public class StudyRoomRankVO {

    /**
     * 排行列表
     */
    private List<StudyRoomRankUserVO> rankUserList;
}
