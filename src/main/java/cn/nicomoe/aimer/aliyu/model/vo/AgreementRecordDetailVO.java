package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordDetailRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 协议历史版本详情（{@code GET /agreement/record/{recordId}}）。
 */
@Data
@AutoMapper(target = AgreementRecordDetailRespDTO.class)
public class AgreementRecordDetailVO {

    /**
     * 会员协议（HTML）
     */
    private String userAgreement;

    /**
     * 隐私条款（HTML）
     */
    private String privacyTerm;

    /**
     * 个人信息跨境授权协议（HTML，站点开启跨境协议时才有内容）
     */
    private String crossBorderAgreement;
}
