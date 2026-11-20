package cn.nicomoe.aimer.aliyu.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

/**
 * 阿里鱼用户设置写请求：绑定/换绑凭证。
 * <p>只需粘贴 {@code X-MZ-SESSION}，其余运行态凭证由运行时自动获取。
 */
@Data
public class AliyuUserSettingReqDTO {

    /**
     * 短时会话标识（X-MZ-SESSION），登录态请求必须携带，从已登录的小程序请求头复制
     */
    @NotBlank(message = "session 不能为空")
    @Size(max = 128, message = "session 长度不能超过{max}个字符")
    @ToString.Exclude
    private String session;
}
