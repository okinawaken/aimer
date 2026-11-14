package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/** 进入签退结算（/mp/study/settlement） */
@Data
public class StudyRoomSettlementClientReqDTO {

    /** 当前签到ID（必填） */
    private Long userSignId;

}
