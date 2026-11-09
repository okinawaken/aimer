package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseOrderClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 储物柜租赁订单
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetLeaseOrderClientRespDTO.class)
public class StudyRoomCabinetLeaseOrderVO {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 租赁ID
     */
    private Long leaseId;

    /**
     * 门店名称
     */
    private String venueName;

    /**
     * 柜号
     */
    private String cabinetNo;

    /**
     * 开始日期
     */
    private String startDateStr;

    /**
     * 结束日期
     */
    private String endDateStr;

    /**
     * 金额（元）
     */
    private BigDecimal priceYuan;

    /**
     * 支付状态
     */
    private Integer payState;

    /**
     * 支付状态名称
     */
    private String payStateStr;

    /**
     * 创建时间
     */
    private String createTimeStr;
}
