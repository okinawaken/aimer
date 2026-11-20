package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 登录用户信息响应（{@code GET /user/loginUser}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserRespDTO {

    /**
     * 应用内用户 ID
     */
    private String appUid;

    /**
     * 登录 token
     */
    private String authToken;

    /**
     * token 有效期毫秒数
     */
    private Long authTokenExpire;

    /**
     * 客户等级 ID
     */
    private String customerLevelId;

    /**
     * 抖音用户 ID
     */
    private String douyinUserId;

    /**
     * 名
     */
    private String firstName;

    /**
     * 星系等级 ID
     */
    private String galaxyGegLevelId;

    /**
     * 上次登录时间（时间字段，保持 String）
     */
    private String lastLoginTime;

    /**
     * 姓
     */
    private String lastName;

    /**
     * 最近操作时间（时间字段，保持 String）
     */
    private String lastOperateTime;

    /**
     * 授权码
     */
    private String oauthCode;

    /**
     * 第三方 openId
     */
    private String openId;

    /**
     * 权限类型
     */
    private Long permissionType;

    /**
     * 合作伙伴 ID
     */
    private String ptnrId;

    /**
     * 当前生效的协议记录 ID，可空；兼容 {@code record_id} 别名
     */
    @JsonAlias(value = "record_id")
    private String recordId;

    /**
     * 滑动验证 checkId
     */
    private String slideStrawberrycheckId;

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 手机号
     */
    private String userTel;

    /**
     * 用户 token
     */
    private String userToken;

    /**
     * 微信 openId
     */
    private String wechatOpenId;
}
