package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.TradeAddCartGoodRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 加入购物车响应（{@code POST /trade/addCartGood}）。
 */
@Data
@AutoMapper(target = TradeAddCartGoodRespDTO.class)
public class TradeAddCartGoodVO {

    /**
     * 购物车流水 token（写缓存 {@code tcis}，供 {@code /shoppingCart/checkGoodSale} 使用）
     */
    private String token;

    /**
     * 合计金额（元字符串）
     */
    private String totalMoneyAmount;

    /**
     * 合计积分（字符串）
     */
    private String totalIntegrationAmount;

    /**
     * 失效商品列表（{@code di()} 逐项拼接告警文案）
     */
    private List<InvalidGoodVO> invalidGoodVos;

    /**
     * 失效商品。
     */
    @Data
    public static class InvalidGoodVO {

        /**
         * 项目别名（优先于 {@code projectName} 展示）
         */
        private String aliasName;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 票品别名（优先于 {@code priceName} 展示）
         */
        private String priceAlias;

        /**
         * 票品名称
         */
        private String priceName;

        /**
         * 楼层名（会场座位信息）
         */
        private String floorName;

        /**
         * 区域名（会场座位信息）
         */
        private String boundName;

        /**
         * 排号（会场座位信息）
         */
        private String rowName;

        /**
         * 座位号（会场座位信息）
         */
        private String seatName;

        /**
         * 库存状态：字符串 {@code "2"} 表示无库存，其余表示已失效
         */
        private String stockState;
    }
}
