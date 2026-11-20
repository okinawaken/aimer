package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.BasicSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentCdnReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentCdnRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentHomepageSettingReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentPageConfigReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentPageConfigRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.HomepageSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.SiteDetailRespDTO;

/**
 * 阿里鱼内容 / 首页配置域服务，返回已解包的业务数据。
 */
public interface AliyuContentService {

    /**
     * 获取 CDN 配置。
     */
    ContentCdnRespDTO getCdn(ContentCdnReqDTO query);

    /**
     * 基础设置。
     */
    BasicSettingRespDTO getBasicSetting();

    /**
     * 首页配置。
     */
    HomepageSettingRespDTO getHomepageSetting(ContentHomepageSettingReqDTO query);

    /**
     * 资讯详情。
     */
    HomepageSettingRespDTO.HomeInformation getInformationDetail(String homeInformationId);

    /**
     * 站点详情。
     */
    SiteDetailRespDTO getSiteDetail(String siteId);

    /**
     * 页面配置。
     */
    ContentPageConfigRespDTO getPageConfig(ContentPageConfigReqDTO query);

    /**
     * 回跳地址。
     */
    String getBackUrl(ContentPageConfigReqDTO query);

    /**
     * 服务器当前时间戳（用于校准本地时间）。
     */
    String currentTimestamp();
}
