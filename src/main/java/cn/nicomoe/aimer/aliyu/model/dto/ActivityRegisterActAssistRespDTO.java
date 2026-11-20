package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 裂变抽奖助力响应（{@code POST /aliyu/act/registerActAssist}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityRegisterActAssistRespDTO {

    /**
     * 助力是否成功（布尔）
     */
    private Boolean assistResult;

    /**
     * 助力结果文案（成功与失败的提示共用）
     */
    private String assistMessage;
}