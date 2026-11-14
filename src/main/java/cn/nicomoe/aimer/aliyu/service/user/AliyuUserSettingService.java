package cn.nicomoe.aimer.aliyu.service.user;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserSettingReqDTO;

/** 阿里鱼用户设置服务，以系统用户 ID 作为资源 ID。 */
public interface AliyuUserSettingService {

    /** 读取用户配置，无记录时返回默认值。 */
    AliyuUserConfigDTO get(Long userId);

    /** 覆盖用户配置。写入前以传入的凭证束调用 {@code /user/loginUser} 校验有效性， 校验失败则抛出{@link cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum#CREDENTIAL_INVALID}。 */
    void update(Long userId, AliyuUserSettingReqDTO req);
}
