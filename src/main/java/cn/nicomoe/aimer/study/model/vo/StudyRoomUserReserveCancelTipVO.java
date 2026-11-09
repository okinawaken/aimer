package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveCancelTipClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 取消预约提示（/mp/user/userReserve/cancel/tip）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomUserReserveCancelTipClientRespDTO.class)
public class StudyRoomUserReserveCancelTipVO {

    /**
     * 确认弹窗文案
     */
    private String tipDesc;

    /**
     * 预约记录ID
     */
    private Long userReserveId;
}
