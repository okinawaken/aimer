package cn.nicomoe.aimer.aliyu.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/** 阿里鱼用户级配置，对应 {@code aliyu_user_setting.config_json}。 只保存用户提供的持久会话 Cookie 与 {@code X-MZ-SESSION}；其他认证头在运行时获取。 baxia 头含时效性信号，不保存或注入抓包值。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuUserConfigBO {

    /** 持久会话 Cookie（JSESSIONID + MZCONSUMERJSESSIONID + MZOPERATEID 拼接），凭据，禁止输出日志 */
    @ToString.Exclude
    private String cookie;

    /** maitix 会话标识（{@code X-MZ-SESSION} 头），凭据，禁止输出日志。 必须由用户提供；缺少时 {@code /user/loginUser} 返回 {@code data:null}、业务接口返回 {@code 804 NO_LOGIN}， 无法从匿名会话响应头恢复登录态。 */
    @ToString.Exclude
    private String sessValue;
}
