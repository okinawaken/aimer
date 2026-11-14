package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 场次详情（{@code GET /pro/event}），同时作为项目详情、按日期查场次中 {@code eventVoList} 的元素结构。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectEventRespDTO {

    /** 场次 token */
    private String eventToken;

    /** 场次名称 */
    private String eventCaption;

    /** 场次变更类型：1 改期、2 取消 */
    private Long eventChangeTag;

    /** 售卖状态：1 即将开售、3 暂不可售 */
    private Long saleState;

    /** 是否售罄 */
    private Boolean sellOut;

    /** 是否开放预约：1 开放 */
    private Long registerEnableState;

    /** 开售预约状态：1 已预约 */
    private Long saleRegisterState;

    /** 倒计时状态：1 倒计时中 */
    private Long countDownState;

    /** 倒计时结束时间 */
    private String countDownEndTime;

    /** 客户端时间（毫秒），用于倒计时校准 */
    private Long cTimeMillis;

    /** 是否展示开售提醒 */
    private Boolean eventSaleRemindShow;

    /** 自动开售时间（毫秒时间戳） */
    private Long autoSaleStartTime;

    /** 优惠标记：{@code "1"} 表示有场次优惠 */
    private String discountMark;

    /** 变更原因（取消/改期公告文案） */
    private String changeReason;

    /** 取消公告图片 */
    private String announceImageUrl;

    /** 环境校验 ID */
    private String cid;

    /** 场次凭证 token（写入本地存储 {@code tgca}） */
    private String token;

    /** 渠道限购数量 */
    private Long channelLimit;

    /** 场次限购数量（同 {@link ProjectEventRespDTO#channelLimit}，数值比较） */
    private Long eventLimitTotal;

    /** 项目限购数量（同 {@link ProjectEventRespDTO#channelLimit}，数值比较） */
    private Long projectLimitTotal;

    /** 订单优惠说明 */
    private List<String> orderDiscount;

    /** 凭证（价格）列表 */
    private List<PriceVo> priceVoList;

    /** 场次开始时间（毫秒时间戳） */
    private Long showTime;

    /** 场次结束时间（毫秒时间戳） */
    private Long showEndTime;

    /** 原始开始时间（毫秒时间戳，改期前） */
    private Long eventOriginalStartTime;

    /** 原始结束时间（毫秒时间戳，改期前） */
    private Long eventOriginalEndTime;

    /** 凭证（价格）。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PriceVo {

        /** 凭证 ID */
        private String priceId;

        /** 凭证名称 */
        private String priceName;

        /** 价格（页面直接展示，未做数值运算） */
        private String price;

        /** 优惠说明（有值时展示原价 + 优惠说明） */
        private String couponDesc;

        /** 券类型：2/3 为套票 */
        private Long couponTicket;

        /** 早鸟票标记：1 为早鸟票 */
        private Long earlyBirdTicketTag;

        /** 凭证优惠文案 */
        private String priceDiscount;

        /** 学生票标记：2 为学生票 */
        private Long priceClassify;

        /** 是否售罄 */
        private Boolean sellOut;

        /** 价格类型：2 为套票/组合价 */
        private Long priceType;

        /** 子凭证 ID 列表（套票加购时作为 {@code childPriceIds} 提交） */
        private List<String> childPriceIds;

        /** 剩余可购套数 */
        private Long marginCount;
    }
}
