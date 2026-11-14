package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 场馆详情（{@code GET /cms/homepage/getSiteDetail}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteDetailRespDTO {

    /** 在售演出场次数 */
    private Long canSaleEventsNum;

    /** 场馆轮播图列表 */
    private List<String> detailSiteImageUrls;

    /** 首页展示的场馆图片 */
    private String homePageSiteImageUrl;

    /** 场馆地址 */
    private String siteAddress;

    /** 场馆 ID */
    private String siteId;

    /** 场馆介绍（HTML 富文本） */
    private String siteIntroduce;

    /** 场馆名称 */
    private String siteName;

    /** 场馆服务标签列表 */
    private List<String> siteServices;
}