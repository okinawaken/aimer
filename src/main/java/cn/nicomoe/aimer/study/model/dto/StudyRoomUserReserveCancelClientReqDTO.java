package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/**
 * 取消预约（/mp/user/userReserve/cancel）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomUserReserveCancelClientReqDTO {

    /**
     * 预约记录ID（取自提交预约返回的 data.id）（必填）
     */
    private Long userReserveId;
}
