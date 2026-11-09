package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 取消预约提示（/mp/user/userReserve/cancel/tip）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomUserReserveCancelTipClientRespDTO {

    /**
     * 确认弹窗文案
     */
    private String tipDesc;

    /**
     * 预约记录ID
     */
    private Long userReserveId;
}
