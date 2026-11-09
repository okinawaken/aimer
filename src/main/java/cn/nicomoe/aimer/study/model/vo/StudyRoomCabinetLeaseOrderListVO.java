package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseOrderListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 储物柜租赁订单列表（/mp/cabinetGrip/v2/lease/order/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetLeaseOrderListClientRespDTO.class)
public class StudyRoomCabinetLeaseOrderListVO {

    /**
     * 订单列表
     */
    private List<StudyRoomCabinetLeaseOrderVO> list;
}
