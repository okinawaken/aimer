package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 报名参与裂变抽奖活动请求（{@code POST /aliyu/act/registerActParticipate}），需要 CSRF，错误由调用方处理。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityRegisterActParticipateReqDTO {

    /** 活动 ID */
    private String actId;
}