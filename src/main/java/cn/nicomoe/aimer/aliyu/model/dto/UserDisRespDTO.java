package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 联系人 / 收件人列表元素（{@code GET /user/getDis} 的 {@code dataList} 项）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDisRespDTO {

    /** 联系人 / 收件人 ID（同时用作列表 key） */
    private String disId;

    /** 收件人姓名 */
    private String receiverName;

    /** 联系电话 */
    private String telPhone;

    /** 邮箱（新增/编辑地址时回填到表单） */
    private String email;

    /** 是否默认地址：1 是 */
    private Integer defaultDis;

    /** 是否默认地址（布尔） */
    private Boolean isdefault;

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

    /** 区名称 */
    private String areaName;

    /** 一级行政区（非中国大陆时使用） */
    private String regionFirst;

    /** 二级行政区（非中国大陆时使用） */
    private String regionSecond;

    /** 三级行政区（非中国大陆时使用） */
    private String regionThird;

    /** 详细地址 */
    private String receiverAddress;

    /** 地址校验状态，{@code 1} 表示需完善地址 */
    private Integer addressCheckStatus;

    /** 电话国际区号（编辑回填到区号选择器） */
    private String countryAreaCode;
}
