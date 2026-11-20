package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 注销协议响应（{@code GET /agreement/deregistration}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeregistrationRespDTO {

    /**
     * 协议分组列表（外层为分组，内层为具体协议）
     */
    private List<AgreementGroup> agreementList;

    /**
     * 租户级注销说明（HTML）
     */
    private String tenantAgreementDetail;

    /**
     * 租户级注销说明标题
     */
    private String tenantAgreementTitle;

    /**
     * 协议分组。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AgreementGroup {

        /**
         * 分组内的协议列表
         */
        private List<AgreementItem> agreementList;
    }

    /**
     * 协议项。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AgreementItem {

        /**
         * 协议类型，注销说明固定取 {@code "DEREGISTRATION_USER_A"}
         */
        private String type;

        /**
         * 协议正文（HTML）
         */
        private String agreementDetail;
    }
}
