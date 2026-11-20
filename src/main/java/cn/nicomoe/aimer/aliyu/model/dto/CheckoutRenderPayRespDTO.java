package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 收银台渲染数据响应（{@code GET /maipay2/renderPay}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutRenderPayRespDTO {

    /**
     * 订单超时时间（毫秒时间戳）
     */
    private Long orderFailureTime;

    /**
     * 服务端当前时间（毫秒时间戳）
     */
    private Long systemTime;

    /**
     * 可用支付方式列表
     */
    private List<PayTypeVO> payTypeVOList;

    /**
     * 订单金额
     */
    private String orderMoney;

    /**
     * 支付方式
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PayTypeVO {

        /**
         * 卖座支付方式 ID
         */
        private String maiPayId;

        /**
         * 支付渠道 ID
         */
        private String payChannelId;

        /**
         * 支付供应商编码
         */
        private String paySupplierCode;

        /**
         * 支付渠道名称
         */
        private String gateWayName;

        /**
         * 支付方式图标
         */
        private String icon;

        /**
         * 支付渠道名称 i18n key
         */
        private String i18nKey;
    }
}
