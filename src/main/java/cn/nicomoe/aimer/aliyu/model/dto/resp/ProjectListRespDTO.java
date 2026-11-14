package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectListRespDTO {
    private java.util.List<DataObj> dataList;
    private Long endRow;
    private Boolean nextPage;
    private Long page;
    private Long pageSize;
    private Long startRow;
    private Long totalPage;
    private Long totalRow;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataObj {
        private String aliasName;
        private Boolean allowChooseSeat;
        private Object buttonState;
        private Object buttonStateDes;
        private Object canAddCart;
        private Object childTicketDesc;
        private Object childTicketType;
        private String cityId;
        private String cityName;
        private Object contactNumber;
        private Long countDownBizType;
        private String countDownEndTime;
        private String countDownEndTimeMillis;
        private Long countDownShowDay;
        private Object countDownShowTime;
        private Object countDownShowTimeMillis;
        private Long countDownState;
        private Object depositDesc;
        private Object depositType;
        private Object detailMaxDiscountInfo;
        private Object discountInfo;
        private Long endTime;
        private Object entryTimeDesc;
        private Object entryTimeType;
        private Object eventVoList;
        private Object grayRecommendDowngrade;
        private Object guideState;
        private Object guideStateDes;
        private String imgUrl;
        private Object isHighlightProject;
        private Long isMx;
        private Object landscapeImgUrl;
        private Object latitude;
        private Object longitude;
        private Object lowestPriceDesc;
        private String maxPrice;
        private String minPrice;
        private Object partHideCart;
        private Long playType;
        private PrivilegeBuyMap privilegeBuyMap;
        private Boolean privilegeBuyState;
        private Long projectClass;
        private Object projectDetail;
        private Object projectDetailDefaultUrl;
        private Object projectDetailUrl;
        private Object projectDiscountInfo;
        private Long projectDiscountMark;
        private Long projectMark;
        private String projectName;
        private Boolean projectRealNameLimit;
        private String projectRealNameLimitCount;
        private Object projectStateName;
        private Object projectTermsFrequency;
        private Long projectTermsStatus;
        private String projectToken;
        private String projectTypeId;
        private String projectTypeName;
        private String provinceId;
        private Object refundProjectRuleVo;
        private Object restrictedItem;
        private Boolean saleRemindShow;
        private Long saleState;
        private String seatMapUrl;
        private Long seatType;
        private Boolean sellOut;
        private String serviceToken;
        private Object shareDesc;
        private Object shareImg;
        private Object shareTitle;
        private Object showTimeDesc;
        private Object showTimeType;
        private String siteAddress;
        private Long siteId;
        private String siteName;
        private Object sort;
        private Long startTime;
        private Boolean studentDiscountMark;
        private String systemTime;
        private Object tip;
        private Object userDiscount;
        private Object userDiscountMap;
        private Object weiboShareContent;
        private Object workTime;
        private Boolean wxSubscribeNotifyEnabled;
        private Boolean wxSubscribeNotifyEnabledCanSubscribe;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PrivilegeBuyMap {
    }
}
