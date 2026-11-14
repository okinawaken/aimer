package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/** 取消预约（/mp/user/userReserve/cancel） */
@Data
public class StudyRoomUserReserveCancelClientReqDTO {

    /** 预约记录ID（取自提交预约返回的 data.id）（必填） */
    private Long userReserveId;
}
