package cn.nicomoe.aimer.aliyu.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/**
 * 阿里鱼用户级配置，对应 {@code aliyu_user_setting.config_json}。
 * <p>只保存长期有效的持久会话 Cookie。{@code X-MZ-SESSION}（分钟级）、操作者 ID、
 * CSRF 等短时凭证由运行态缓存维护，不落库。凭据字段禁止输出到日志。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuUserSettingConfigBO {

    /** 持久会话 Cookie（JSESSIONID + MZCONSUMERJSESSIONID 等），凭据，禁止输出日志 */
    @ToString.Exclude
    private String cookie;
}
