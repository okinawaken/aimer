package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.RenderConfigRespDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 下单页渲染配置响应（{@code POST /trade/renderConfig}）。
 */
@Data
@AutoMapper(target = RenderConfigRespDTO.class)
public class RenderConfigVO {

    /**
     * 订单条款（协议）信息
     */
    private CmsTicketClauseVO cmsTicketClause;

    /**
     * 条款列表
     */
    private List<CmsTicketClauseVO> cmsTicketClauseList;

    /**
     * 国家列表
     */
    private List<JsonNode> countryList;

    /**
     * 支付网关（支付方式）列表
     */
    private List<CheckoutRenderPayVO.PayTypeVO> pay2GateWayList;

    /**
     * 地区列表
     */
    private List<JsonNode> regionList;

    /**
     * 订单条款（协议）。
     */
    @Data
    public static class CmsTicketClauseVO {

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
