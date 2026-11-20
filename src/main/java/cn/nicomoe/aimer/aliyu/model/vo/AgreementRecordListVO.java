package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 协议历史版本列表元素（{@code GET /agreement/record/list}），首项为当前版本。
 */
@Data
@AutoMapper(target = AgreementRecordListRespDTO.class)
public class AgreementRecordListVO {

    /**
     * 协议记录 ID（跳转历史详情 {@code /agreement/record/{recordId}} 时使用）
     */
    private String recordId;

    /**
     * 版本号文案（页面直接展示）
     */
    private String version;
}
