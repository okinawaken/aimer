package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterActDetailRespDTO {
    private String actId;
    private String actName;
    private Long actStatus;
    private String actStatusDesc;
    private Long actType;
    private String backgroundColor;
    private Long beginTime;
    private Object canSubscribe;
    private String coverUrl;
    private Long dataState;
    private Object description;
    private Long drawStatus;
    private Object drawTime;
    private Long endTime;
    private Long exchangeEndTime;
    private Long exchangeStartTime;
    private String ext;
    private Object helpCount;
    private String id;
    private Object invitationRecords;
    private Long inviteCodeLimit;
    private String mainPicUrl;
    private Object myCodes;
    private String projectId;
    private String projectName;
    private String ptnrId;
    private Object publishTime;
    private String registerCodeCount;
    private Object registerUser;
    private java.util.List<RuleDtolist> ruleDTOList;
    private String ruleUrl;
    private String shareImageUrl;
    private Object signUpEndTime;
    private Object signUpMessage;
    private Long stage;
    private Long status;
    private String templateId;
    private String tenantId;
    private Long testFlag;
    private Object venueId;
    private String venueName;
    private Long wechatNotify;
    private java.util.List<String> winCodes;
    private Boolean wxSubscribeNotifyEnabled;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RuleDtolist {
        private String actId;
        private Long dataState;
        private Long drawRule;
        private Object drawTime;
        private Long exchangeEndTime;
        private Long exchangeStartTime;
        private String id;
        private String inspireValue;
        private String ptnrId;
        private Object publishTime;
        private String tenantId;
    }
}
