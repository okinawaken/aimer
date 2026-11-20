package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 可开票订单列表响应（{@code GET /invoice/simple/getInvoicingOrderList}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoicingOrderListRespDTO {

    /**
     * 可开票订单列表
     */
    private List<InvoicingOrderItem> dataList;

    /**
     * 结束行
     */
    private Long endRow;

    /**
     * 是否有下一页
     */
    private Boolean nextPage;

    /**
     * 当前页码
     */
    private Long page;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 起始行
     */
    private Long startRow;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总条数
     */
    private Long totalRow;

    /**
     * 可开票订单项。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InvoicingOrderItem {

        /**
         * 订单 ID（点击「去申请」时作为路由参数）
         */
        private String orderId;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 场馆名称
         */
        private String siteName;

        /**
         * 可开票金额（「元」字符串，直接拼接展示）
         */
        private String invoiceTotalAmountStr;

        /**
         * 项目图片
         */
        private String projectImgUrl;

        /**
         * 场次名称
         */
        private String eventName;
    }
}