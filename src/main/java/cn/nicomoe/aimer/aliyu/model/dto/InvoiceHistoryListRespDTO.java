package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 已开票记录分页（{@code GET /invoice/simple/getInvoiceInfoList}）。 <p> 与 {@link InvoicingOrderListRespDTO}（可开票订单）字段不同，这里对应发票历史页的行数据。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceHistoryListRespDTO {

    /**
     * 当前页发票记录
     */
    private List<InvoiceHistoryItem> dataList;

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 是否还有下一页；为 {@code true} 时以 {@code page + 1} 继续请求
     */
    private Boolean nextPage;

    /**
     * 发票历史行，小程序侧状态 0/1 显示“开票中”，5 显示“已开票”。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InvoiceHistoryItem {

        /**
         * 发票记录 ID，查询详情时作为 {@code tradeInvoiceId} 传入
         */
        private String tradeInvoiceId;

        /**
         * 开票状态
         */
        private Integer invoiceStatus;

        /**
         * 开票时间
         */
        private String invoiceTime;

        /**
         * 发票金额
         */
        private String invoiceTotalAmount;

        /**
         * 发票抬头（付款方名称）
         */
        private String payerName;
    }
}
