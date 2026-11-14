package cn.nicomoe.aimer.aliyu.client.http.v2;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
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
import com.dtflys.forest.annotation.Var;

/** 内容 / 首页配置域（显式用户上下文）：{@code /cms}、{@code /pageConfig}、{@code /common}，启动链路调用。 */
public interface AliyuContentV2Client extends AliyuBaseClient {

    /** 获取 CDN 配置。 参数 query：资源类型（1=项目 / 2=场次）、项目 token、场次日期参数 userId：系统用户 ID */
    @Get(url = "/cms/getCdn")
    AliyuClientResult<ContentCdnRespDTO> getCdn(@Query ContentCdnReqDTO query,
                                                @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 基础设置。 */
    @Get(url = "/cms/basicSetting/getBasicSetting")
    AliyuClientResult<BasicSettingRespDTO> getBasicSetting(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 首页配置。 */
    @Get(url = "/cms/homepage/getHomepageSetting")
    AliyuClientResult<HomepageSettingRespDTO> getHomepageSetting(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 资讯详情。 */
    @Get(url = "/cms/homepage/getInformationDetail")
    AliyuClientResult<HomepageSettingRespDTO.HomeInformation> getInformationDetail(@Query(value = "homeInformationId") String homeInformationId,
                                                                                   @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 站点详情。 */
    @Get(url = "/cms/homepage/getSiteDetail")
    AliyuClientResult<SiteDetailRespDTO> getSiteDetail(@Query(value = "siteId") String siteId,
                                                       @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 页面配置。 参数 query：页面类型，首页传 {@code home} 参数 userId：系统用户 ID */
    @Get(url = "/pageConfig/getPageConfig")
    AliyuClientResult<ContentPageConfigRespDTO> getPageConfig(@Query ContentPageConfigReqDTO query,
                                                              @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 回跳地址（响应 data 即为静态资源 URL）。 参数 query：页面类型，与 {@link #getPageConfig} 一致参数 userId：系统用户 ID */
    @Get(url = "/pageConfig/getBackUrl")
    AliyuClientResult<String> getBackUrl(@Query ContentPageConfigReqDTO query,
                                         @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 服务器当前时间戳（用于校准本地时间）。 */
    @Get(url = "/common/currentTimestamp")
    AliyuClientResult<String> currentTimestamp(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);
}
