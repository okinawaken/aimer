package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 注销会员请求（{@code POST /aliyupoint/deregisterUser}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointDeregisterUserReqDTO {

    /**
     * 短信验证码（输入框去除空白后的值）
     */
    private String msgCode;

    /**
     * 已绑定手机号（取自 {@code /user/userContact} 的 {@code userTel}）
     */
    private String mobile;

    /**
     * 国家/地区区号，默认 {@code 86}（由 {@code countryAreaCode} 去掉前导 {@code +} 得到）
     */
    private String countryAreaCode;
}
