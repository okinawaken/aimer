package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import cn.nicomoe.aimer.aliyu.model.dto.resp.BasicSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.HomepageSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.SiteDetailRespDTO;

/**
 * 内容 / 首页配置域：{@code /cms}、{@code /pageConfig}、{@code /common}。启动链路调用。
 * <p>
 * 继承 {@link AliyuBaseClient}，故与其他业务接口一样要求已绑定凭证束（全量鉴权）。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuContentClient extends AliyuBaseClient {

    /**
     * 获取 CDN 配置。
     *
     * @return 响应数据
     */
    @Get(url = "/cms/getCdn")
    AliyuClientResult<Object> getCdn();

    /**
     * 基础设置。
     *
     * @return 响应数据
     */
    @Get(url = "/cms/basicSetting/getBasicSetting")
    AliyuClientResult<BasicSettingRespDTO> getBasicSetting();

    /**
     * 首页配置。
     *
     * @return 响应数据
     */
    @Get(url = "/cms/homepage/getHomepageSetting")
    AliyuClientResult<HomepageSettingRespDTO> getHomepageSetting();

    /**
     * 资讯详情。
     *
     * @param homeInformationId 资讯 ID
     * @return 响应数据
     */
    @Get(url = "/cms/homepage/getInformationDetail")
    AliyuClientResult<Object> getInformationDetail(@Query(value = "homeInformationId") String homeInformationId);

    /**
     * 站点详情。
     *
     * @param siteId 站点 ID
     * @return 响应数据
     */
    @Get(url = "/cms/homepage/getSiteDetail")
    AliyuClientResult<SiteDetailRespDTO> getSiteDetail(@Query(value = "siteId") String siteId);

    /**
     * 页面配置。
     *
     * @return 响应数据
     */
    @Get(url = "/pageConfig/getPageConfig")
    AliyuClientResult<Object> getPageConfig();

    /**
     * 回跳地址。
     *
     * @return 响应数据
     */
    @Get(url = "/pageConfig/getBackUrl")
    AliyuClientResult<Object> getBackUrl();

    /**
     * 服务器当前时间戳（用于校准本地时间）。
     *
     * @return 响应数据
     */
    @Get(url = "/common/currentTimestamp")
    AliyuClientResult<String> currentTimestamp();
}
