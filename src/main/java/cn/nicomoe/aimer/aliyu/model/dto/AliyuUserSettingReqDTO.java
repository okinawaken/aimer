package cn.nicomoe.aimer.aliyu.model.dto;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** 阿里鱼用户设置写请求：只收真机 Cookie 与 {@code X-MZ-SESSION}，整对象覆盖（未传即清空）。 */
@Data
@AutoMapper(target = AliyuUserConfigBO.class)
public class AliyuUserSettingReqDTO {

    /** 持久会话 Cookie（JSESSIONID + MZCONSUMERJSESSIONID + MZOPERATEID） */
    @Size(max = 4096, message = "cookie 长度不能超过{max}个字符")
    private String cookie;

    /** maitix 会话标识（{@code X-MZ-SESSION} 头），缺失时上游一律判定未登录 */
    @Size(max = 128, message = "会话标识长度不能超过{max}个字符")
    private String sessValue;
}
