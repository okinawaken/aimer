package cn.nicomoe.aimer.aliyu.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/**
 * 阿里鱼用户级配置，短时会话标识存放于 Redis（key 见 {@link cn.nicomoe.aimer.aliyu.constant.AliyuCacheConstants.Credential}）。
 * <p>{@code X-MZ-SESSION} 为分钟级短时凭证，由响应头持续刷新。
 * 操作者 ID、CSRF 等运行态凭证由运行态缓存维护，不在此配置中。凭据字段禁止输出到日志。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuUserSettingConfigBO {

    /**
     * 短时会话标识（X-MZ-SESSION），凭据，禁止输出日志
     */
    @ToString.Exclude
    private String session;
}
