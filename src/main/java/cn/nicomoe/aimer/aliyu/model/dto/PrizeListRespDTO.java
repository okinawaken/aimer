package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 积分商城奖品列表响应（{@code GET /aliyuMember/pointsMall/prize/list}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrizeListRespDTO {

    /** 奖品列表 */
    private java.util.List<Item> dataList;

    /** 结束行号 */
    private Long endRow;

    /** 当前页码 */
    private Long page;

    /** 每页数量 */
    private Long pageSize;

    /** 起始行号 */
    private Long startRow;

    /** 总页数 */
    private Long totalPage;

    /** 总记录数 */
    private Long totalRow;

    /** 奖品列表项。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {

        /** 奖品 ID（详情页路由参数与兑换入参） */
        private Long pointsPrizeId;

        /** 奖品类型（mock：{@code 1} 实物、{@code 2} 券） */
        private Long prizeType;

        /** 关联商品/券 ID（mock 中的 {@code SPU-MOCK-xxx}） */
        private String refId;

        /** 奖品名称 */
        private String prizeName;

        /** 奖品图片 */
        private String prizePic;

        /** 兑换所需积分（mock 为数值，卡片直接展示） */
        private Long pointsPrice;

        /** 积分文案 */
        private String pointsPriceText;

        /** 剩余库存（mock 为数值） */
        private Long remainStock;

        /** 可兑换状态：{@code 0} 可兑换；非 0 表示不可兑换（卡片按钮置灰）。 详情页对 {@code exchangeable} 进一步区分 {@code 1/2/4/5} 等业务不可兑换原因。 */
        private Long exchangeable;

        /** 可兑换状态文案 */
        private String exchangeableText;
    }
}