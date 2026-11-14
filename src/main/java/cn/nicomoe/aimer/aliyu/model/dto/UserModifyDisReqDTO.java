package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 修改联系人请求（{@code POST /user/modifyDis}），{@code email/telPhone} 为 RSA 加密字段。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModifyDisReqDTO {

    /** 收件人姓名（未修改则传 null） */
    private String receiverName;

    /** 国家/地区 ID */
    private String countryId;

    /** 省 ID */
    private String provinceId;

    /** 市 ID */
    private String cityId;

    /** 区 ID */
    private String areaId;

    /** 是否默认地址（布尔） */
    private Boolean isdefault;

    /** 详细地址 */
    private String receiverAddress;

    /** 联系人 ID */
    private String disId;

    /** 邮箱（RSA 加密字段） */
    private String email;

    /** 电话国际区号 */
    private String countryAreaCode;

    /** 联系电话（RSA 加密字段，与原始值不同才传） */
    private String telPhone;
}
