package org.dromara.aimer.repository.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.dromara.aimer.repository.base.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 社会化关系表
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_social")
public class SysSocialDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 租户id
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 平台+平台唯一id
     */
    @TableField("auth_id")
    private String authId;

    /**
     * 用户来源
     */
    @TableField("source")
    private String source;

    /**
     * 平台编号唯一id
     */
    @TableField("open_id")
    private String openId;

    /**
     * 登录账号
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 头像地址
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 用户的授权令牌
     */
    @TableField("access_token")
    private String accessToken;

    /**
     * 用户的授权令牌的有效期，部分平台可能没有
     */
    @TableField("expire_in")
    private Integer expireIn;

    /**
     * 刷新令牌，部分平台可能没有
     */
    @TableField("refresh_token")
    private String refreshToken;

    /**
     * 平台的授权信息，部分平台可能没有
     */
    @TableField("access_code")
    private String accessCode;

    /**
     * 用户的 unionid
     */
    @TableField("union_id")
    private String unionId;

    /**
     * 授予的权限，部分平台可能没有
     */
    @TableField("scope")
    private String scope;

    /**
     * 个别平台的授权信息，部分平台可能没有
     */
    @TableField("token_type")
    private String tokenType;

    /**
     * id token，部分平台可能没有
     */
    @TableField("id_token")
    private String idToken;

    /**
     * 小米平台用户的附带属性，部分平台可能没有
     */
    @TableField("mac_algorithm")
    private String macAlgorithm;

    /**
     * 小米平台用户的附带属性，部分平台可能没有
     */
    @TableField("mac_key")
    private String macKey;

    /**
     * 用户的授权code，部分平台可能没有
     */
    @TableField("code")
    private String code;

    /**
     * Twitter平台用户的附带属性，部分平台可能没有
     */
    @TableField("oauth_token")
    private String oauthToken;

    /**
     * Twitter平台用户的附带属性，部分平台可能没有
     */
    @TableField("oauth_token_secret")
    private String oauthTokenSecret;

    /**
     * 创建部门
     */
    @TableField("create_dept")
    private Long createDept;

    /**
     * 创建者
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新者
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableField("del_flag")
    private String delFlag;
}
