package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetUserGridClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 我的柜格（已租）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetUserGridClientRespDTO.class)
public class StudyRoomCabinetUserGridVO {

    /**
     * 柜格ID（开锁时用）
     */
    private Long gripId;

    /**
     * 柜格编号
     */
    private String gripCode;

    /**
     * 柜子名称
     */
    private String cabinetName;

    /**
     * 是否允许开锁：1 允许
     */
    private Integer allowOpen;

    /**
     * 退租状态
     */
    private Integer checkoutStatus;

    /**
     * 最近一次租约ID（自动退租时用）
     */
    private Long lastLeaseId;

    /**
     * 租期结束时间
     */
    private String leaseEndTime;

}
