package cn.nicomoe.aimer.aliyu.model.dto;

import lombok.Data;

/** 申请开票请求（{@code POST /invoice/simple/applyInvoice}）。 个人发票（{@code invoiceType="0"}）时 {@code payerRegisterNo} 置空。 {@code buyerTel/email} 为 RSA 加密字段，字段名须保持一致。 */
@Data
public class InvoiceApplyInvoiceReqDTO {

    /** 接收发票的手机号（RSA 加密字段） */
    private String buyerTel;

    /** 接收发票的邮箱（RSA 加密字段） */
    private String email;

    /** 发票抬头类型：0 个人，1 公司 */
    private String invoiceType;

    /** 订单 ID */
    private String orderId;

    /** 发票抬头 */
    private String payerName;

    /** 纳税识别号（公司抬头时必填） */
    private String payerRegisterNo;

    /** 发票备注 */
    private String remark;

    /** 手机号国家区号 */
    private String countryAreaCode;
}
