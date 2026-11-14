package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 联系人详情响应（{@code GET /user/getDisInfo?disId=}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGetDisInfoRespDTO {

    /** 收件人姓名 */
    private String receiverName;

    /** 联系电话 */
    private String telPhone;

    /** 邮箱 */
    private String email;

    /** 国家/地区 ID */
    private String countryId;

    /** 电话国际区号 */
    private String countryAreaCode;

    /** 省 ID */
    private String provinceId;

    /** 市 ID */
    private String cityId;

    /** 区 ID（为 "0" 时表示无区级） */
    private String areaId;

    /** 一级行政区（非中国大陆时使用） */
    private String regionFirst;

    /** 二级行政区（非中国大陆时使用） */
    private String regionSecond;

    /** 三级行政区（非中国大陆时使用） */
    private String regionThird;

    /** 详细地址 */
    private String receiverAddress;

    /** 是否默认地址（布尔） */
    private Boolean isdefault;

    /** 联系人 ID */
    private String disId;
}
