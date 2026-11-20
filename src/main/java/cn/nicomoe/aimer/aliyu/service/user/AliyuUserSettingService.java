package cn.nicomoe.aimer.aliyu.service.user;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserSettingReqDTO;

/**
 * 阿里鱼用户设置服务，以系统用户 ID 作为资源 ID。
 */
public interface AliyuUserSettingService {

    /**
     * 读取用户配置，无记录时返回空配置。 参数 userId：系统用户 ID返回：用户配置
     */
    AliyuUserConfigDTO get(Long userId);

    /**
     * 绑定/换绑凭证：先调用上游校验，再写入缓存。 参数 userId：系统用户 ID参数 req：绑定请求
     */
    void update(Long userId, AliyuUserSettingReqDTO req);

    /**
     * 解绑当前用户凭证：清除该用户全部会话凭证缓存。
     * 参数 userId：系统用户 ID
     */
    void unbind(Long userId);

    /**
     * 检查凭证是否有效：调用上游用户信息接口实测，不保存任何数据。
     */
    String check(String session);
}
