package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/** 学习排行榜（/mp/study/rank/index） */
@Data
public class StudyRoomRankClientReqDTO {

    /** 门店ID */
    private Integer venueId;

    /** 排行类型 */
    private Integer rankType;
}
