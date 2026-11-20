package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 渲染配置请求（{@code POST /trade/renderConfig}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeRenderConfigReqDTO {

    /**
     * 商品类型（2 金额商品 / 3 积分商品）
     */
    private Long goodType;

    /**
     * 国家 ID，固定传 0
     */
    private Long countryId;

    /**
     * 供应商类别
     */
    private String supplierClass;

    /**
     * 语言类型，传 {@code "1"}
     */
    private String langType;

    /**
     * 需要渲染的模块列表，如 {@code ["cmsTicketClause"]}、{@code ["payGateWay"]}
     */
    private List<String> renderModule;
}