package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 可开票订单详情响应（{@code GET /invoice/simple/getInvoicingOrderDetail}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceGetInvoicingOrderDetailRespDTO {

    /** 订单号 */
    private String orderId;

    /** 项目名称 */
    private String projectName;

    /** 购票金额 */
    private String orderTicketMoney;

    /** 快递金额 */
    private String orderExpressMoney;
}
