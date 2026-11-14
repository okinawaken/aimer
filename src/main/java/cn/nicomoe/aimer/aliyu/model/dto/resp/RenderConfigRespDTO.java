package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RenderConfigRespDTO {
    private CmsTicketClause cmsTicketClause;
    private Object cmsTicketClauseList;
    private Object countryList;
    private Object pay2GateWayList;
    private Object regionList;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CmsTicketClause {
        private String ticketClauseContent;
        private String ticketClauseId;
        private Long ticketClauseState;
        private String ticketClauseTitle;
    }
}
