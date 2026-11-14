package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 协议历史版本详情（{@code GET /agreement/record/{recordId}}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementRecordDetailRespDTO {

    /** 会员协议（HTML） */
    private String userAgreement;

    /** 隐私条款（HTML） */
    private String privacyTerm;

    /** 个人信息跨境授权协议（HTML，站点开启跨境协议时才有内容） */
    private String crossBorderAgreement;
}
