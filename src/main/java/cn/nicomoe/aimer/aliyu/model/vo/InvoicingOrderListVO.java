package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.InvoicingOrderListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 可开票订单列表响应（{@code GET /invoice/simple/getInvoicingOrderList}）。
 */
@Data
@AutoMapper(target = InvoicingOrderListRespDTO.class)
public class InvoicingOrderListVO {

    /**
     * 可开票订单列表
     */
    private List<InvoicingOrderItemVO> dataList;

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
    public static class InvoicingOrderItemVO {

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
