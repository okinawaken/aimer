package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.InvoiceGetInvoicingOrderDetailRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 可开票订单详情响应（{@code GET /invoice/simple/getInvoicingOrderDetail}）。
 */
@Data
@AutoMapper(target = InvoiceGetInvoicingOrderDetailRespDTO.class)
public class InvoiceGetInvoicingOrderDetailVO {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 购票金额
     */
    private String orderTicketMoney;

    /**
     * 快递金额
     */
    private String orderExpressMoney;
}
