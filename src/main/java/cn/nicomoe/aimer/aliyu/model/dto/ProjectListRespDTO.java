package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

/** 项目列表（{@code GET /pro/projects}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectListRespDTO {

    /** 项目列表 */
    private List<DataObj> dataList;

    /** 当前页最后一行下标 */
    private Long endRow;

    /** 是否还有下一页（场馆页分页加载使用） */
    private Boolean nextPage;

    /** 当前页码 */
    private Long page;

    /** 每页条数 */
    private Long pageSize;

    /** 当前页起始行下标 */
    private Long startRow;

    /** 总页数 */
    private Long totalPage;

    /** 总条数 */
    private Long totalRow;

    /** 项目卡片数据。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataObj {

        /** 项目别名 */
        private String aliasName;

        /** 是否允许选座 */
        private Boolean allowChooseSeat;

        /** 按钮状态 */
        private Object buttonState;

        /** 按钮状态描述 */
        private Object buttonStateDes;

        /** 是否可加购 */
        private Object canAddCart;

        /** 儿童票说明，{@code childTicketType === 3} 时展示 */
        private String childTicketDesc;

        /** 儿童票类型：0/1/2 使用内置文案，3 使用 {@link #childTicketDesc} */
        private Long childTicketType;

        /** 城市 ID */
        private String cityId;

        /** 城市名称（卡片“城市 | 场馆”） */
        private String cityName;

        /** 客服电话 */
        private Object contactNumber;

        /** 倒计时业务类型 */
        private Long countDownBizType;

        /** 倒计时结束时间（字符串） */
        private String countDownEndTime;

        /** 倒计时结束时间毫秒值 */
        private Long countDownEndTimeMillis;

        /** 距开售天数（{@code >= 1} 展示“N 天后开启售票”） */
        private Long countDownShowDay;

        /** 倒计时展示时间 */
        private Object countDownShowTime;

        /** 倒计时展示时间毫秒值 */
        private Object countDownShowTimeMillis;

        /** 倒计时状态：1 倒计时中 */
        private Long countDownState;

        /** 寄存说明（{@code depositType === 1} 时作为文案展示） */
        private String depositDesc;

        /** 寄存说明类型：0 无寄存处，1 有寄存处 */
        private Long depositType;

        /** 项目级最大优惠信息，兼容单个对象与数组；元素结构见 {@link ProjectDetailRespDTO.DiscountInfo}。 */
        private Object detailMaxDiscountInfo;

        /** 可领取优惠券列表 */
        private List<ProjectDetailRespDTO.DiscountInfo> discountInfo;

        /** 项目结束时间（毫秒时间戳，卡片与 {@code 1 * endTime} 格式化） */
        private Long endTime;

        /** 入场时间说明（{@code entryTimeType === 3} 时展示） */
        private String entryTimeDesc;

        /** 入场时间类型：0 开始前 30 分钟，1 90 分钟，2 120 分钟，3 使用 {@link #entryTimeDesc} */
        private Long entryTimeType;

        /** 场次列表 */
        private List<ProjectEventRespDTO> eventVoList;

        /** 是否降级推荐（为 true 时不展示“为你推荐”） */
        private Boolean grayRecommendDowngrade;

        /** 引导状态 */
        private Object guideState;

        /** 引导状态描述 */
        private Object guideStateDes;

        /** 项目海报 */
        private String imgUrl;

        /** 是否高亮项目 */
        private Object isHighlightProject;

        /** 是否麦秀项目 */
        private Long isMx;

        /** 横版海报 */
        private Object landscapeImgUrl;

        /** 纬度 */
        private Object latitude;

        /** 经度 */
        private Object longitude;

        /** 最低价说明（如“8 折起”，卡片拼成“{$x}折起”标签） */
        private String lowestPriceDesc;

        /** 最高价（直接展示，未做数值运算） */
        private String maxPrice;

        /** 最低价（直接展示，未做数值运算） */
        private String minPrice;

        /** 是否隐藏购物车 */
        private Object partHideCart;

        /** 演出类型 */
        private Long playType;

        /** 特权购买说明：key 为特权描述，value 为该特权可购买的场次名称列表 */
        private Map<String, List<String>> privilegeBuyMap;

        /** 是否存在特权购买 */
        private Boolean privilegeBuyState;

        /** 项目分类 ID（列表请求入参名为 {@code projectClass}） */
        private Long projectClass;

        /** 项目详情富文本（HTML） */
        private Object projectDetail;

        /** 项目详情默认地址 */
        private Object projectDetailDefaultUrl;

        /** 项目详情地址 */
        private Object projectDetailUrl;

        /** 项目优惠信息 */
        private Object projectDiscountInfo;

        /** 项目优惠标记 */
        private Long projectDiscountMark;

        /** 项目标记 */
        private Long projectMark;

        /** 项目名称（卡片标题兜底） */
        private String projectName;

        /** 是否实名限购 */
        private Boolean projectRealNameLimit;

        /** 实名限购数量（页面未做数值运算，按字符串展示/回传） */
        private String projectRealNameLimitCount;

        /** 项目状态名称 */
        private Object projectStateName;

        /** 项目场次频率 */
        private Object projectTermsFrequency;

        /** 项目场次状态 */
        private Long projectTermsStatus;

        /** 项目 token（卡片点击跳转 {@code /pages-order/projectDetail/index?projectId=}） */
        private String projectToken;

        /** 项目分类 ID（字符串形态，分类筛选回传） */
        private String projectTypeId;

        /** 项目分类名称（卡片左上角标签） */
        private String projectTypeName;

        /** 省份 ID */
        private String provinceId;

        /** 退款规则（与详情页同名字段同结构，见 {@link ProjectDetailRespDTO.RefundProjectRule}） */
        private ProjectDetailRespDTO.RefundProjectRule refundProjectRuleVo;

        /** 禁止携带物品说明（{@code restrictedItem} 非空时展示“禁止携带物品”卡片） */
        private String restrictedItem;

        /** 是否展示开售提醒 */
        private Boolean saleRemindShow;

        /** 售卖状态：3 已结束（卡片置灰并展示“已结束”） */
        private Long saleState;

        /** 座位图地址 */
        private String seatMapUrl;

        /** 坐席类型：1 单座、2 多座 */
        private Long seatType;

        /** 是否售罄 */
        private Boolean sellOut;

        /** 服务 token */
        private String serviceToken;

        /** 分享描述 */
        private Object shareDesc;

        /** 分享图片 */
        private Object shareImg;

        /** 分享标题 */
        private Object shareTitle;

        /** 活动时长说明（{@code showTimeType === 3} 时展示） */
        private String showTimeDesc;

        /** 活动时长类型：0 约 90 分钟、1 约 120 分钟、2 以现场为准、3 使用 {@link #showTimeDesc}、4 约 150 分钟 */
        private Long showTimeType;

        /** 场馆地址 */
        private String siteAddress;

        /** 场馆 ID */
        private Long siteId;

        /** 场馆名称（卡片“城市 | 场馆”） */
        private String siteName;

        /** 排序值 */
        private Object sort;

        /** 项目开始时间（毫秒时间戳，卡片格式化展示） */
        private Long startTime;

        /** 学生票标记 */
        private Boolean studentDiscountMark;

        /** 服务端当前时间（字符串，页面用于倒计时校准/订单超时判断） */
        private String systemTime;

        /** 推荐语 */
        private String tip;

        /** 用户优惠 */
        private Object userDiscount;

        /** 用户可用优惠：key 为场次 token，value 为该场次可用的优惠列表 */
        private Map<String, List<ProjectDetailRespDTO.DiscountInfo>> userDiscountMap;

        /** 微博分享内容 */
        private Object weiboShareContent;

        /** 客服工作时间 */
        private Object workTime;

        /** 是否开启微信订阅通知 */
        private Boolean wxSubscribeNotifyEnabled;

        /** 是否可以订阅微信通知 */
        private Boolean wxSubscribeNotifyEnabledCanSubscribe;
    }
}