package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 用户联系方式响应（{@code GET /user/userContact}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserContactRespDTO {

    /** 电话国际区号 */
    private String countryAreaCode;

    /** 邮箱 */
    private String userEmail;

    /** 手机号（注销页据此判断是否已绑定手机） */
    private String userTel;
}
