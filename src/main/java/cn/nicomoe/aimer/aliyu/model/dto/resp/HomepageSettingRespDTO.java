package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomepageSettingRespDTO {
    private Object basicSettingVO;
    private HomepageSettingInfo homepageSettingInfo;
    private String id;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HomepageSettingInfo {
        private Boolean activityCarouselEnable;
        private java.util.List<Object> activityCarouselInfoList;
        private Object activityCarouselSize;
        private Boolean atmosphereEnable;
        private java.util.List<Atmosphere> atmosphereList;
        private Object atmosphereSize;
        private String backgroundColor;
        private java.util.List<Carousel> carouselList;
        private CarouselSize carouselSize;
        private Long carouselState;
        private Object homeBallAreaInfo;
        private Boolean homeBallEnable;
        private Object homeBallLayoutType;
        private Boolean homeInformationEnable;
        private java.util.List<HomeInformation> homeInformationList;
        private String homeInformationName;
        private Boolean homePopUpEnable;
        private Object homePopUpInfo;
        private java.util.List<HomeSiteInfo> homeSiteInfos;
        private Boolean homeSiteState;
        private String hotName;
        private Object hotNameMultiLangList;
        private HotProjectBroadcast hotProjectBroadcast;
        private java.util.List<Object> hotProjectIdList;
        private Boolean hotShowAllEnable;
        private Long hotState;
        private Long listOrCarousel;
        private MarketingActivitiesInfo marketingActivitiesInfo;
        private Long marketingActivitiesState;
        private String moreProjectName;
        private Object moreProjectNameMultiLangList;
        private Long moreProjectState;
        private Long projectCityState;
        private Long projectSiteState;
        private Long projectTypeState;
        private String searchBoxBackgroundColor;
        private Long searchBoxState;
        private Long searchBoxWordColor;
        private java.util.List<String> searchTermsList;
        private java.util.List<Object> searchTermsMultiLangList;
        private Long searchTermsState;
        private java.util.List<Subject> subjectList;
        private String subjectName;
        private Object subjectNameMultiLangList;
        private Long subjectState;
        private Object supportedLanguage;
        private Long wordColor;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Atmosphere {
        private Object appId;
        private String img;
        private Long linkType;
        private Object multiLang;
        private Object projectId;
        private Object projectIdEnable;
        private Object projectName;
        private Object redirectUrl;
        private Object relativePath;
        private Object url;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Carousel {
        private String img;
        private Object multiLang;
        private Object relativePath;
        private String url;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CarouselSize {
        private Long height;
        private Long width;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HomeInformation {
        private String homeInformationContent;
        private String homeInformationId;
        private String homeInformationImageUrl;
        private String homeInformationSubtitle;
        private String homeInformationTitle;
        private Long releaseTime;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HomeSiteInfo {
        private java.util.List<Object> detailSiteImageUrls;
        private String homePageSiteImageUrl;
        private String siteAddress;
        private Object siteId;
        private Object siteIdList;
        private String siteIntroduce;
        private String siteName;
        private java.util.List<Object> siteServices;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HotProjectBroadcast {
        private String name;
        private Long num;
        private java.util.List<Object> projectIds;
        private Boolean state;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MarketingActivitiesInfo {
        private Object columnId;
        private String columnUrl;
        private String customUrl;
        private Object giftBagId;
        private String giftBagUrl;
        private String img;
        private Long pageType;
        private String redirectUrl;
        private Long skipType;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Subject {
        private Object bgColor;
        private Object columnId;
        private String img;
        private Object multiLang;
        private String recommendedLanguage;
        private String title;
    }
}
