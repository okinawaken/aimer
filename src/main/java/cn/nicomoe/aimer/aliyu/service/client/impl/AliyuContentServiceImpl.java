package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuContentClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.BasicSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentCdnReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentCdnRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentPageConfigReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ContentPageConfigRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.HomepageSettingRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.SiteDetailRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/** 阿里鱼内容 / 首页配置域服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuContentServiceImpl implements AliyuContentService {

    private final AliyuContentClient contentClient;

    @Override
    public ContentCdnRespDTO getCdn(ContentCdnReqDTO query) {
        AliyuClientResult<ContentCdnRespDTO> result = contentClient.getCdn(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public BasicSettingRespDTO getBasicSetting() {
        AliyuClientResult<BasicSettingRespDTO> result = contentClient.getBasicSetting();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public HomepageSettingRespDTO getHomepageSetting() {
        AliyuClientResult<HomepageSettingRespDTO> result = contentClient.getHomepageSetting();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public HomepageSettingRespDTO.HomeInformation getInformationDetail(String homeInformationId) {
        AliyuClientResult<HomepageSettingRespDTO.HomeInformation> result = contentClient.getInformationDetail(homeInformationId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public SiteDetailRespDTO getSiteDetail(String siteId) {
        AliyuClientResult<SiteDetailRespDTO> result = contentClient.getSiteDetail(siteId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ContentPageConfigRespDTO getPageConfig(ContentPageConfigReqDTO query) {
        AliyuClientResult<ContentPageConfigRespDTO> result = contentClient.getPageConfig(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public String getBackUrl(ContentPageConfigReqDTO query) {
        AliyuClientResult<String> result = contentClient.getBackUrl(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public String currentTimestamp() {
        AliyuClientResult<String> result = contentClient.currentTimestamp();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
