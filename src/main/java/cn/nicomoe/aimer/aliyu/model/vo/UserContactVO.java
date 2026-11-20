package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.UserContactRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 用户联系方式响应（{@code GET /user/userContact}）。
 */
@Data
@AutoMapper(target = UserContactRespDTO.class)
public class UserContactVO {

    /**
     * 电话国际区号
     */
    private String countryAreaCode;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 手机号（注销页据此判断是否已绑定手机）
     */
    private String userTel;
}
