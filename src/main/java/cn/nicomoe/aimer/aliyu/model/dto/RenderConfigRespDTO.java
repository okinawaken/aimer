package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

/**
 * 下单页渲染配置响应（{@code POST /trade/renderConfig}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RenderConfigRespDTO {

    /**
     * 订单条款（协议）信息
     */
    private CmsTicketClause cmsTicketClause;

    /**
     * 条款列表
     */
    private List<CmsTicketClause> cmsTicketClauseList;

    /**
     * 国家列表
     */
    private List<JsonNode> countryList;

    /**
     * 支付网关（支付方式）列表
     */
    private List<CheckoutRenderPayRespDTO.PayTypeVO> pay2GateWayList;

    /**
     * 地区列表
     */
    private List<JsonNode> regionList;

    /**
     * 订单条款（协议）。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CmsTicketClause {

        /**
         * 条款富文本正文
         */
        private String ticketClauseContent;

        /**
         * 条款 ID
         */
        private String ticketClauseId;

        /**
         * 条款状态
         */
        private Long ticketClauseState;

        /**
         * 条款标题
         */
        private String ticketClauseTitle;
    }
}
