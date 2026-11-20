package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.AgreementDetailRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 协议详情响应（{@code GET /agreement/detail}），协议记录 ID 兼容 {@code record_id} 别名。
 */
@Data
@AutoMapper(target = AgreementDetailRespDTO.class)
public class AgreementDetailVO {

    /**
     * 协议分组列表
     */
    private List<AgreementGroupVO> agreementList;

    /**
     * 本次更新内容（HTML 文本）
     */
    private String changeStatement;

    /**
     * 个人信息跨境授权协议（HTML 文本）
     */
    private String crossBorderAgreement;

    /**
     * 是否需要用户确认协议
     */
    private Boolean needUserConfirm;

    /**
     * 隐私条款（HTML 文本）
     */
    private String privacyTerm;

    /**
     * 合作伙伴 ID
     */
    private String ptnrId;

    /**
     * 当前生效的协议记录 ID
     */
    private String recordId;

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 会员协议（HTML 文本）
     */
    private String userAgreement;

    /**
     * 协议分组。
     */
    @Data
    public static class AgreementGroupVO {

        /**
         * 分组内协议列表
         */
        private List<AgreementItemVO> agreementList;
    }

    /**
     * 协议项。
     */
    @Data
    public static class AgreementItemVO {

        /**
         * 协议类型（如 {@code DEREGISTRATION_USER_A}）
         */
        private String type;

        /**
         * 协议正文（HTML 文本）
         */
        private String agreementDetail;
    }
}
