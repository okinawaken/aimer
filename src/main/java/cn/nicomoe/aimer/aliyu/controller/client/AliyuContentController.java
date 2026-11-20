package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.BasicSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentCdnReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentCdnRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentHomepageSettingReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentPageConfigReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentPageConfigRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.HomepageSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.SiteDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.vo.BasicSettingVO;
import cn.nicomoe.aimer.aliyu.model.vo.ContentCdnVO;
import cn.nicomoe.aimer.aliyu.model.vo.ContentPageConfigVO;
import cn.nicomoe.aimer.aliyu.model.vo.HomeInformationVO;
import cn.nicomoe.aimer.aliyu.model.vo.HomepageSettingVO;
import cn.nicomoe.aimer.aliyu.model.vo.SiteDetailVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuContentService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 阿里鱼内容 / 首页配置域接口。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/content")
public class AliyuContentController {

    private final AliyuContentService contentService;

    /**
     * 获取 CDN 配置。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/cdn")
    public R<ContentCdnVO> getCdn(ContentCdnReqDTO query) {
        ContentCdnRespDTO cdnDTO = contentService.getCdn(query);
        ContentCdnVO cdnVO = MapstructUtils.convert(cdnDTO, ContentCdnVO.class);
        return R.ok(cdnVO);
    }

    /**
     * 基础设置。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/basic-setting")
    public R<BasicSettingVO> getBasicSetting() {
        BasicSettingRespDTO basicSettingDTO = contentService.getBasicSetting();
        BasicSettingVO basicSettingVO = MapstructUtils.convert(basicSettingDTO, BasicSettingVO.class);
        return R.ok(basicSettingVO);
    }

    /**
     * 首页配置。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/homepage-setting")
    public R<HomepageSettingVO> getHomepageSetting(ContentHomepageSettingReqDTO query) {
        HomepageSettingRespDTO homepageSettingDTO = contentService.getHomepageSetting(query);
        HomepageSettingVO homepageSettingVO = MapstructUtils.convert(homepageSettingDTO, HomepageSettingVO.class);
        return R.ok(homepageSettingVO);
    }

    /**
     * 资讯详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/information/detail")
    public R<HomeInformationVO> getInformationDetail(
            @RequestParam(value = "homeInformationId") String homeInformationId) {
        HomepageSettingRespDTO.HomeInformation informationDTO = contentService.getInformationDetail(homeInformationId);
        HomeInformationVO informationVO = MapstructUtils.convert(informationDTO, HomeInformationVO.class);
        return R.ok(informationVO);
    }

    /**
     * 站点详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/site/detail")
    public R<SiteDetailVO> getSiteDetail(@RequestParam(value = "siteId") String siteId) {
        SiteDetailRespDTO siteDetailDTO = contentService.getSiteDetail(siteId);
        SiteDetailVO siteDetailVO = MapstructUtils.convert(siteDetailDTO, SiteDetailVO.class);
        return R.ok(siteDetailVO);
    }

    /**
     * 页面配置。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/page-config")
    public R<ContentPageConfigVO> getPageConfig(ContentPageConfigReqDTO query) {
        ContentPageConfigRespDTO pageConfigDTO = contentService.getPageConfig(query);
        ContentPageConfigVO pageConfigVO = MapstructUtils.convert(pageConfigDTO, ContentPageConfigVO.class);
        return R.ok(pageConfigVO);
    }

    /**
     * 回跳地址。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/back-url")
    public R<String> getBackUrl(ContentPageConfigReqDTO query) {
        return R.ok(contentService.getBackUrl(query));
    }

    /**
     * 服务器当前时间戳（用于校准本地时间）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/current-timestamp")
    public R<String> currentTimestamp() {
        return R.ok(contentService.currentTimestamp());
    }
}
