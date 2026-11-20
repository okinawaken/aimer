package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 用户信息查询请求（{@code GET /user/userInfo}）。 小程序查询会员积分等场景会携带语言类型，其余场景可不传。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoReqDTO {

    /**
     * 语言类型：1 中文
     */
    private Integer langType;
}
