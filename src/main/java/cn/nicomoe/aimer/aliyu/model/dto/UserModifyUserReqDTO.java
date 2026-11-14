package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 修改用户信息请求（{@code POST /user/modifyUser}）。 {@code userCard/emergencyContact/emergencyContactTelphone} 由拦截器逐字段 RSA 加密。 仅提交被修改的字段，调用方须控制其他空字段的序列化。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModifyUserReqDTO {

    /** 昵称（个人信息页改名时单独提交） */
    private String userName;

    /** 性别（个人信息页改性别时单独提交，1 男 / 2 女） */
    private Integer userSex;

    /** 出生日期（个人信息页改生日时单独提交，格式 yyyy-MM-dd） */
    private String userBirthday;

    /** 证件号（RSA 加密字段） */
    private String userCard;

    /** 紧急联系人（RSA 加密字段） */
    private String emergencyContact;

    /** 紧急联系人电话（RSA 加密字段） */
    private String emergencyContactTelphone;

    /** 国家/地区 ID */
    private String countryId;

    /** 国家/地区名称 */
    private String countryName;

    /** 省 ID */
    private String provinceId;

    /** 省名称 */
    private String provinceName;

    /** 市 ID */
    private String cityId;

    /** 市名称 */
    private String cityName;

    /** 区 ID */
    private String areaId;
}
