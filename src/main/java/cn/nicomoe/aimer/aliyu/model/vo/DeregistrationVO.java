package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.DeregistrationRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 注销协议响应（{@code GET /agreement/deregistration}）。
 */
@Data
@AutoMapper(target = DeregistrationRespDTO.class)
public class DeregistrationVO {

    /**
     * 协议分组列表（外层为分组，内层为具体协议）
     */
    private List<AgreementGroupVO> agreementList;

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
    public static class AgreementGroupVO {

        /**
         * 分组内的协议列表
         */
        private List<AgreementItemVO> agreementList;
    }

    /**
     * 协议项。
     */
    @Data
    public static class AgreementItemVO {

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
