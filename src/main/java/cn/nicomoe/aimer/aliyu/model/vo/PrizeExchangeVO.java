package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.PrizeExchangeRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 奖品兑换响应（{@code POST /aliyuMember/pointsMall/prize/exchange}）。 响应的 {@code success/message} 与统一信封的 {@code code/msg} 并存。
 */
@Data
@AutoMapper(target = PrizeExchangeRespDTO.class)
public class PrizeExchangeVO {

    /**
     * 是否兑换成功
     */
    private Boolean success;

    /**
     * 失败文案（{@code success === false} 时展示）
     */
    private String message;

    /**
     * 兑换结果载体；存在时使用 {@code date} 内的结果，否则使用顶层同名字段。
     */
    private PrizeExchangeResultVO date;

    /**
     * 可兑换状态：{@code 0} 成功、{@code -1} 失败、其它值表示仍有未满足条件
     */
    private Long exchangeable;

    /**
     * 可兑换状态文案
     */
    private String exchangeableText;

    /**
     * 奖品 ID（数值）
     */
    private Long pointsPrizeId;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品图片
     */
    private String prizePic;

    /**
     * 兑换消耗积分（数值）
     */
    private Long pointsPrice;

    /**
     * 兑换订单 ID
     */
    private Long orderId;

    /**
     * 兑换订单号
     */
    private String orderNo;

    /**
     * 兑换结果（{@code date} 字段结构，与响应顶层结果字段一致）。
     */
    @Data
    public static class PrizeExchangeResultVO {

        /**
         * 可兑换状态：{@code 0} 成功、{@code -1} 失败
         */
        private Long exchangeable;

        /**
         * 可兑换状态文案
         */
        private String exchangeableText;

        /**
         * 奖品 ID（数值）
         */
        private Long pointsPrizeId;

        /**
         * 奖品名称
         */
        private String prizeName;

        /**
         * 奖品图片
         */
        private String prizePic;

        /**
         * 兑换消耗积分（数值）
         */
        private Long pointsPrice;

        /**
         * 兑换订单 ID
         */
        private Long orderId;

        /**
         * 兑换订单号
         */
        private String orderNo;
    }
}
