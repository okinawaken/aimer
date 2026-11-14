package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuUserInfoRespDTO {
    private String accountMoney;
    private String accountOpenState;
    private String accountState;
    private Object age;
    private Object areaId;
    private Object areaName;
    private Object basicInformationCompleted;
    private Object cardIdType;
    private Object cardIdTypeName;
    private Object cityId;
    private Object cityName;
    private String cost;
    private String countryAreaCode;
    private String countryId;
    private String countryName;
    private Object countryRegionId;
    private Object countryRegionName;
    private Object deregisterAgreement;
    private Object emergencyContact;
    private Object emergencyContactCountryAreaCode;
    private Object emergencyContactTelphone;
    private Object fillPhone;
    private Object firstName;
    private Object forbidAlterInformation;
    private String growthValue;
    private String lastLoginTime;
    private Object lastName;
    private Long level;
    private String levelCode;
    private String levelName;
    private Object levelNum;
    private Object levelPic;
    private Long memberFlag;
    private Object modifyPayPasswordTime;
    private Object nationalityId;
    private Object nationalityName;
    private Boolean needSupplementCardInfo;
    private Long payPasswordLevel;
    private String point;
    private Object postalCode;
    private Object provinceId;
    private Object provinceName;
    private Long pwdStrength;
    private Object rechargeLevelName;
    private String rechargeTotalMoney;
    private Object remark;
    private Object showDeregisterButton;
    private Object thirdUserToken;
    private Object userAddress;
    private Object userBirthday;
    private Object userCard;
    private Object userEmail;
    private String userLevel;
    private String userName;
    private Object userPhoto;
    private Long userSex;
    private String userState;
    private String userTel;
    private String userToken;


}
