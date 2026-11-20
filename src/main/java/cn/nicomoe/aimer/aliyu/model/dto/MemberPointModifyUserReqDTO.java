package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 修改会员资料请求（{@code POST /aliyupoint/modifyUser}）。 仅提交被修改的字段，其余字段可空。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointModifyUserReqDTO {

    /**
     * 昵称（最长 20 字符）
     */
    private String userName;

    /**
     * 性别：{@code 1} 男 / {@code 2} 女
     */
    private Integer userSex;

    /**
     * 生日，格式 {@code YYYY-MM-DD}
     */
    private String userBirthday;
}
