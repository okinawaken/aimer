package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActAssistRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 裂变抽奖助力响应（{@code POST /aliyu/act/registerActAssist}）。
 */
@Data
@AutoMapper(target = ActivityRegisterActAssistRespDTO.class)
public class ActivityRegisterActAssistVO {

    /**
     * 助力是否成功（布尔）
     */
    private Boolean assistResult;

    /**
     * 助力结果文案（成功与失败的提示共用）
     */
    private String assistMessage;
}
