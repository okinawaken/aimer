package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.BasicSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentCdnReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentCdnRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentPageConfigReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentPageConfigRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.HomepageSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.SiteDetailRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;

/** 内容 / 首页配置域。 */
public interface AliyuContentClient extends AliyuBaseClient {

    /** 获取 CDN 配置。 */
    @Get(url = "/cms/getCdn")
    AliyuClientResult<ContentCdnRespDTO> getCdn(@Query ContentCdnReqDTO query);

    /** 基础设置。 */
    @Get(url = "/cms/basicSetting/getBasicSetting")
    AliyuClientResult<BasicSettingRespDTO> getBasicSetting();

    /** 首页配置。 */
    @Get(url = "/cms/homepage/getHomepageSetting")
    AliyuClientResult<HomepageSettingRespDTO> getHomepageSetting();

    /** 资讯详情。 */
    @Get(url = "/cms/homepage/getInformationDetail")
    AliyuClientResult<HomepageSettingRespDTO.HomeInformation> getInformationDetail(@Query(value = "homeInformationId") String homeInformationId);

    /** 站点详情。 */
    @Get(url = "/cms/homepage/getSiteDetail")
    AliyuClientResult<SiteDetailRespDTO> getSiteDetail(@Query(value = "siteId") String siteId);

    /** 页面配置。 */
    @Get(url = "/pageConfig/getPageConfig")
    AliyuClientResult<ContentPageConfigRespDTO> getPageConfig(@Query ContentPageConfigReqDTO query);

    /** 回跳地址。 */
    @Get(url = "/pageConfig/getBackUrl")
    AliyuClientResult<String> getBackUrl(@Query ContentPageConfigReqDTO query);

    /** 服务器当前时间戳（用于校准本地时间）。 */
    @Get(url = "/common/currentTimestamp")
    AliyuClientResult<String> currentTimestamp();
}
