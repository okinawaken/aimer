package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/**
 * 学习排行榜（/mp/study/rank/index）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomRankClientReqDTO {

    /**
     * 门店ID
     */
    private Integer venueId;

    /**
     * 排行类型
     */
    private Integer rankType;
}
