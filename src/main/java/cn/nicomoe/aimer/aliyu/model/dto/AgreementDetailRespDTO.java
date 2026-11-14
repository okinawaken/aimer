package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 协议详情响应（{@code GET /agreement/detail}），协议记录 ID 兼容 {@code record_id} 别名。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementDetailRespDTO {

    /** 协议分组列表 */
    private Object agreementList;

    /** 本次更新内容（HTML 文本） */
    private String changeStatement;

    /** 个人信息跨境授权协议（HTML 文本） */
    private String crossBorderAgreement;

    /** 是否需要用户确认协议 */
    private Boolean needUserConfirm;

    /** 隐私条款（HTML 文本） */
    private String privacyTerm;

    /** 合作伙伴 ID */
    private String ptnrId;

    /** 当前生效的协议记录 ID */
    @JsonAlias(value = "record_id")
    private String recordId;

    /** 租户 ID */
    private String tenantId;

    /** 会员协议（HTML 文本） */
    private String userAgreement;
}
