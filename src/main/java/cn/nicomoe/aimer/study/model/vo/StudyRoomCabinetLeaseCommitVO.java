package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseCommitClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 储物柜下单结果（/mp/cabinetGrip/v2/lease/commit）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetLeaseCommitClientRespDTO.class)
public class StudyRoomCabinetLeaseCommitVO {

    /**
     * 租约ID（随后调 lease/pay 时作为 leaseId）
     */
    private Long id;

    /**
     * 支付单ID
     */
    private Long payId;

}
