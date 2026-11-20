package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.CheckoutDoPayRespDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 发起支付响应（{@code POST /maipay2/doPay}）。
 */
@Data
@AutoMapper(target = CheckoutDoPayRespDTO.class)
public class CheckoutDoPayVO {

    /**
     * 支付方式类型（如 {@code WeixinMiniAppNative}、{@code AliMiniNative}）
     */
    private String type;

    /**
     * 支付参数：微信小程序为支付参数对象，支付宝小程序为 tradeNO 字符串
     */
    private JsonNode data;

    /**
     * 外部支付单号
     */
    private String outerPaymentNo;
}
