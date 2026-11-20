package cn.nicomoe.aimer.aliyu.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

/**
 * 阿里鱼用户设置写请求：绑定/换绑凭证。
 * <p>只需粘贴 Cookie；{@code X-MZ-SESSION} 等短时凭证由运行时自动获取，不接受用户输入。
 */
@Data
public class AliyuUserSettingReqDTO {

    /** 持久会话 Cookie（JSESSIONID + MZCONSUMERJSESSIONID 等），从已登录的小程序请求头复制 */
    @NotBlank(message = "cookie 不能为空")
    @Size(max = 4096, message = "cookie 长度不能超过{max}个字符")
    @ToString.Exclude
    private String cookie;
}
