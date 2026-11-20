package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 阿里鱼会员信息响应（{@code GET /aliyupoint/userInfo}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuUserInfoRespDTO {

    /**
     * 账户余额（金额字符串）
     */
    private String accountMoney;

    /**
     * 账户开通状态
     */
    private String accountOpenState;

    /**
     * 账户状态
     */
    private String accountState;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 区 ID
     */
    private String areaId;

    /**
     * 区名称
     */
    private String areaName;

    /**
     * 基本资料是否填写完整（布尔）
     */
    private Boolean basicInformationCompleted;

    /**
     * 证件类型
     */
    private String cardIdType;

    /**
     * 证件类型名称
     */
    private String cardIdTypeName;

    /**
     * 市 ID
     */
    private String cityId;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 城市
     */
    private String city;

    /**
     * 费用（金额字符串）
     */
    private String cost;

    /**
     * 国家/地区
     */
    private String country;

    /**
     * 电话国际区号
     */
    private String countryAreaCode;

    /**
     * 国家/地区 ID
     */
    private String countryId;

    /**
     * 国家/地区名称
     */
    private String countryName;

    /**
     * 国家/地区层级 ID
     */
    private String countryRegionId;

    /**
     * 国家/地区层级名称
     */
    private String countryRegionName;

    /**
     * 注销协议内容（HTML 文本）
     */
    private String deregisterAgreement;

    /**
     * 紧急联系人（RSA 加密字段）
     */
    private String emergencyContact;

    /**
     * 紧急联系人国家区号
     */
    private String emergencyContactCountryAreaCode;

    /**
     * 紧急联系人电话（RSA 加密字段）
     */
    private String emergencyContactTelphone;

    /**
     * 补全手机号
     */
    private String fillPhone;

    /**
     * 名
     */
    private String firstName;

    /**
     * 是否禁止修改资料（布尔）
     */
    private Boolean forbidAlterInformation;

    /**
     * 成长值
     */
    private String growthValue;

    /**
     * 上次登录时间（时间字段，保持 String）
     */
    private String lastLoginTime;

    /**
     * 姓
     */
    private String lastName;

    /**
     * 会员等级数值
     */
    private Long level;

    /**
     * 会员等级编码
     */
    private String levelCode;

    /**
     * 会员等级名称
     */
    private String levelName;

    /**
     * 等级序号
     */
    private Integer levelNum;

    /**
     * 等级图片
     */
    private String levelPic;

    /**
     * 会员标记
     */
    private Long memberFlag;

    /**
     * 支付密码修改时间（时间字段，保持 String）
     */
    private String modifyPayPasswordTime;

    /**
     * 国籍 ID
     */
    private String nationalityId;

    /**
     * 国籍名称
     */
    private String nationalityName;

    /**
     * 是否需要补充证件信息
     */
    private Boolean needSupplementCardInfo;

    /**
     * 支付密码等级
     */
    private Long payPasswordLevel;

    /**
     * 积分
     */
    private String point;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 省
     */
    private String province;

    /**
     * 省 ID
     */
    private String provinceId;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 密码强度
     */
    private Long pwdStrength;

    /**
     * 一级行政区
     */
    private String regionFirst;

    /**
     * 二级行政区
     */
    private String regionSecond;

    /**
     * 三级行政区
     */
    private String regionThird;

    /**
     * 充值等级名称
     */
    private String rechargeLevelName;

    /**
     * 累计充值金额（金额字符串）
     */
    private String rechargeTotalMoney;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否展示注销按钮
     */
    private Boolean showDeregisterButton;

    /**
     * 第三方用户 token
     */
    private String thirdUserToken;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 区 ID
     */
    private String userAreaId;

    /**
     * 出生日期
     */
    private String userBirthday;

    /**
     * 证件号（RSA 加密字段）
     */
    private String userCard;

    /**
     * 市 ID
     */
    private String userCityId;

    /**
     * 市名称
     */
    private String userCityName;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 用户等级
     */
    private String userLevel;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 头像地址
     */
    private String userPhoto;

    /**
     * 省 ID
     */
    private String userProvinceId;

    /**
     * 省名称
     */
    private String userProvinceName;

    /**
     * 性别
     */
    private Long userSex;

    /**
     * 用户状态
     */
    private String userState;

    /**
     * 手机号
     */
    private String userTel;

    /**
     * 用户 token
     */
    private String userToken;
}
