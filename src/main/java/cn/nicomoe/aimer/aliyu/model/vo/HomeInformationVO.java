package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.HomepageSettingRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 首页资讯（{@code /cms/homepage/getInformationDetail} 亦返回该结构）。
 */
@Data
@AutoMapper(target = HomepageSettingRespDTO.HomeInformation.class)
public class HomeInformationVO {

    /**
     * 资讯正文（HTML 富文本，rich-text 渲染）
     */
    private String homeInformationContent;

    /**
     * 资讯 ID
     */
    private String homeInformationId;

    /**
     * 资讯封面图
     */
    private String homeInformationImageUrl;

    /**
     * 资讯副标题
     */
    private String homeInformationSubtitle;

    /**
     * 资讯标题
     */
    private String homeInformationTitle;

    /**
     * 发布时间
     */
    private Long releaseTime;
}
