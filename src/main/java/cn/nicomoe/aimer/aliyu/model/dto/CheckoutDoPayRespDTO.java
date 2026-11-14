package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 发起支付响应（{@code POST /maipay2/doPay}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutDoPayRespDTO {

    /** 支付方式类型（如 {@code WeixinMiniAppNative}、{@code AliMiniNative}） */
    private String type;

    /** 支付参数：微信小程序为支付参数对象，支付宝小程序为 tradeNO 字符串 */
    private Object data;

    /** 外部支付单号 */
    private String outerPaymentNo;
}
