package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 历史发票详情（{@code GET /invoice/simple/getInvoiceDetails}）。 <p> 与 {@link InvoiceGetInvoicingOrderDetailRespDTO}（可开票订单详情）不是同一份数据； 小程序历史详情页以 {@code invoiceCode} 初始化卡片，再用响应中的 {@code orderId} 覆盖。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceHistoryDetailRespDTO {

    /** 订单号；历史详情页未取到该字段时以发票代码占位 */
    private String orderId;

    /** 开票状态 */
    private Integer invoiceStatus;

    /** 发票代码 */
    private String invoiceCode;

    /** 项目名称 */
    private String projectName;

    /** 购票金额 */
    private String orderTicketMoney;

    /** 快递金额 */
    private String orderExpressMoney;

    /** 发票抬头（付款方名称） */
    private String payerName;

    /** 付款方纳税人识别号 */
    private String payerRegisterNo;

    /** 接收发票的邮箱 */
    private String email;

    /** 国家地区编码 */
    private String countryAreaCode;

    /** 购买方电话 */
    private String buyerTel;

    /** 备注 */
    private String remark;

    /** 开票时间 */
    private String invoiceTime;
}
