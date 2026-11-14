package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterActListRespDTO {
    private java.util.List<DataObj> dataList;
    private Long endRow;
    private Boolean nextPage;
    private Long page;
    private Long pageSize;
    private Long startRow;
    private Long totalPage;
    private Long totalRow;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataObj {
        private String actId;
        private String actName;
        private Long actType;
        private Object backgroundColor;
        private Long beginTime;
        private Object canSubscribe;
        private String coverUrl;
        private Object dataState;
        private Object description;
        private Long drawStatus;
        private Object drawTime;
        private Long endTime;
        private Long exchangeEndTime;
        private Long exchangeStartTime;
        private String ext;
        private Object helpCount;
        private Object id;
        private Object invitationRecords;
        private Object inviteCodeLimit;
        private Object mainPicUrl;
        private Object myCodes;
        private String projectId;
        private String projectName;
        private String ptnrId;
        private Object publishTime;
        private Object registerCodeCount;
        private Object ruleDTOList;
        private Object ruleUrl;
        private Object shareImageUrl;
        private Object signUpEndTime;
        private Object signUpMessage;
        private Long stage;
        private Long status;
        private String tenantId;
        private Object testFlag;
        private Object venueId;
        private String venueName;
        private Object wechatNotify;
        private Object winCodes;
        private Object wxSubscribeNotifyEnabled;
    }
}
