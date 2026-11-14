package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/** 换座座位图（/mp/study/change/seat/list） */
@Data
public class StudyRoomChangeSeatClientReqDTO {

    /** 当前签到ID（必填） */
    private Long userSignId;
}
