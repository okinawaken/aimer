package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserRespDTO {
    private String appUid;
    private Object authToken;
    private Object authTokenExpire;
    private String customerLevelId;
    private Object douyinUserId;
    private Object firstName;
    private Object galaxyGegLevelId;
    private Object lastLoginTime;
    private Object lastName;
    private String lastOperateTime;
    private String oauthCode;
    private Object openId;
    private Long permissionType;
    private String ptnrId;
    private String recordId;
    private Object slideStrawberrycheckId;
    private String tenantId;
    private String userName;
    private Object userTel;
    private String userToken;
    private String wechatOpenId;


}
