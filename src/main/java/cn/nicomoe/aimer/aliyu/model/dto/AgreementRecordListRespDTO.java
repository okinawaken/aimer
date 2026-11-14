package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 协议历史版本列表元素（{@code GET /agreement/record/list}），首项为当前版本。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementRecordListRespDTO {

    /** 协议记录 ID（跳转历史详情 {@code /agreement/record/{recordId}} 时使用） */
    private String recordId;

    /** 版本号文案（页面直接展示） */
    private String version;
}
