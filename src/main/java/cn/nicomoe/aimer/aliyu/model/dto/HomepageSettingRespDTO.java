package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 首页配置（{@code GET /cms/homepage/getHomepageSetting}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomepageSettingRespDTO {

    /** 基础设置（与 {@code /cms/basicSetting/getBasicSetting} 同域的站点配置） */
    private Object basicSettingVO;

    /** 首页楼层配置 */
    private HomepageSettingInfo homepageSettingInfo;

    /** 配置 ID */
    private String id;

    /** 首页楼层配置。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HomepageSettingInfo {

        /** 活动轮播开关 */
        private Boolean activityCarouselEnable;

        /** 活动轮播内容列表 */
        private List<Object> activityCarouselInfoList;

        /** 活动轮播尺寸 */
        private Object activityCarouselSize;

        /** 氛围图开关 */
        private Boolean atmosphereEnable;

        /** 氛围图列表 */
        private List<Atmosphere> atmosphereList;

        /** 氛围图尺寸 */
        private Object atmosphereSize;

        /** 首页背景色 */
        private String backgroundColor;

        /** 轮播图列表 */
        private List<Carousel> carouselList;

        /** 轮播图尺寸 */
        private CarouselSize carouselSize;

        /** 轮播状态 */
        private Long carouselState;

        /** 球区配置 */
        private Object homeBallAreaInfo;

        /** 球区开关 */
        private Boolean homeBallEnable;

        /** 球区布局类型 */
        private Object homeBallLayoutType;

        /** 首页资讯开关 */
        private Boolean homeInformationEnable;

        /** 首页资讯列表 */
        private List<HomeInformation> homeInformationList;

        /** 资讯模块名称（资讯详情页作为导航栏标题） */
        private String homeInformationName;

        /** 首页弹窗开关 */
        private Boolean homePopUpEnable;

        /** 首页弹窗配置 */
        private Object homePopUpInfo;

        /** 首页站点列表 */
        private List<HomeSiteInfo> homeSiteInfos;

        /** 首页站点状态 */
        private Boolean homeSiteState;

        /** 热门模块名称 */
        private String hotName;

        /** 热门模块名称多语言配置 */
        private Object hotNameMultiLangList;

        /** 热门播报配置 */
        private HotProjectBroadcast hotProjectBroadcast;

        /** 热门项目 ID 列表 */
        private List<Object> hotProjectIdList;

        /** 是否热门全部展示 */
        private Boolean hotShowAllEnable;

        /** 热门模块状态 */
        private Long hotState;

        /** 列表/轮播展示方式 */
        private Long listOrCarousel;

        /** 营销活动配置 */
        private MarketingActivitiesInfo marketingActivitiesInfo;

        /** 营销活动状态 */
        private Long marketingActivitiesState;

        /** “更多”入口文案（列表页筛选面板“更多项目”按钮） */
        private String moreProjectName;

        /** “更多”入口多语言配置 */
        private Object moreProjectNameMultiLangList;

        /** “更多”入口状态 */
        private Long moreProjectState;

        /** 城市筛选状态：2 隐藏“城市”筛选项 */
        private Long projectCityState;

        /** 场馆筛选状态 */
        private Long projectSiteState;

        /** 分类筛选状态：2 隐藏“分类”筛选项（列表页） */
        private Long projectTypeState;

        /** 是否展示“分类”筛选 */
        private Boolean showProjectType;

        /** 搜索框背景色 */
        private String searchBoxBackgroundColor;

        /** 搜索框状态 */
        private Long searchBoxState;

        /** 搜索框文字颜色 */
        private Long searchBoxWordColor;

        /** 搜索热词列表 */
        private List<String> searchTermsList;

        /** 搜索热词多语言配置 */
        private List<Object> searchTermsMultiLangList;

        /** 搜索热词状态 */
        private Long searchTermsState;

        /** 专题列表 */
        private List<Subject> subjectList;

        /** 专题模块名称 */
        private String subjectName;

        /** 专题模块名称多语言配置 */
        private Object subjectNameMultiLangList;

        /** 专题状态 */
        private Long subjectState;

        /** 支持的语言 */
        private Object supportedLanguage;

        /** 文字颜色 */
        private Long wordColor;
    }

    /** 氛围图。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Atmosphere {

        /** 小程序 appId */
        private Object appId;

        /** 氛围图地址 */
        private String img;

        /** 跳转类型 */
        private Long linkType;

        /** 多语言配置 */
        private Object multiLang;

        /** 关联项目 ID */
        private Object projectId;

        /** 是否启用关联项目 */
        private Object projectIdEnable;

        /** 关联项目名称 */
        private Object projectName;

        /** 跳转地址 */
        private Object redirectUrl;

        /** 页面相对路径 */
        private Object relativePath;

        /** 页面路径 */
        private Object url;
    }

    /** 轮播图。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Carousel {

        /** 轮播图地址 */
        private String img;

        /** 多语言配置 */
        private Object multiLang;

        /** 页面相对路径 */
        private Object relativePath;

        /** 跳转地址 */
        private String url;
    }

    /** 轮播图尺寸。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CarouselSize {

        /** 高度 */
        private Long height;

        /** 宽度 */
        private Long width;
    }

    /** 首页资讯（{@code /cms/homepage/getInformationDetail} 亦返回该结构）。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HomeInformation {

        /** 资讯正文（HTML 富文本，rich-text 渲染） */
        private String homeInformationContent;

        /** 资讯 ID */
        private String homeInformationId;

        /** 资讯封面图 */
        private String homeInformationImageUrl;

        /** 资讯副标题 */
        private String homeInformationSubtitle;

        /** 资讯标题 */
        private String homeInformationTitle;

        /** 发布时间 */
        private Long releaseTime;
    }

    /** 首页站点（字段与 {@link SiteDetailRespDTO} 同源）。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HomeSiteInfo {

        /** 场馆轮播图列表 */
        private List<String> detailSiteImageUrls;

        /** 首页展示的场馆图片 */
        private String homePageSiteImageUrl;

        /** 场馆地址 */
        private String siteAddress;

        /** 场馆 ID */
        private String siteId;

        /** 场馆 ID 列表 */
        private Object siteIdList;

        /** 场馆介绍（HTML 富文本） */
        private String siteIntroduce;

        /** 场馆名称 */
        private String siteName;

        /** 场馆服务标签列表 */
        private List<String> siteServices;
    }

    /** 热门播报。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HotProjectBroadcast {

        /** 播报名称 */
        private String name;

        /** 播报数量 */
        private Long num;

        /** 项目 ID 列表 */
        private List<Object> projectIds;

        /** 开关状态 */
        private Boolean state;
    }

    /** 营销活动配置。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MarketingActivitiesInfo {

        /** 栏目 ID */
        private Object columnId;

        /** 栏目地址 */
        private String columnUrl;

        /** 自定义地址 */
        private String customUrl;

        /** 福袋 ID */
        private Object giftBagId;

        /** 福袋地址 */
        private String giftBagUrl;

        /** 活动图片 */
        private String img;

        /** 页面类型 */
        private Long pageType;

        /** 跳转地址 */
        private String redirectUrl;

        /** 跳转类型 */
        private Long skipType;
    }

    /** 专题。 */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Subject {

        /** 背景色 */
        private Object bgColor;

        /** 栏目 ID */
        private Object columnId;

        /** 专题图片 */
        private String img;

        /** 多语言配置 */
        private Object multiLang;

        /** 推荐语言 */
        private String recommendedLanguage;

        /** 专题标题 */
        private String title;
    }
}