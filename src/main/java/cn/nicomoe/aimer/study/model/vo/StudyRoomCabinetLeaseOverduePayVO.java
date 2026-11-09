package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseOverduePayClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 储物柜逾期支付（/mp/cabinetGrip/v2/lease/overdue/pay）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetLeaseOverduePayClientRespDTO.class)
public class StudyRoomCabinetLeaseOverduePayVO {

    /**
     * 支付单ID
     */
    private Long payId;

    /**
     * 微信支付参数
     */
    private StudyRoomWxPayParamVO payParam;

}
