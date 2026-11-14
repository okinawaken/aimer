package cn.nicomoe.aimer.aliyu.service.user;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserSettingReqDTO;

/**
 * 阿里鱼用户设置服务。
 * <p>
 * 用户设置以系统用户 ID 作为资源 ID，详情与修改接口的 ID 参数即为用户 ID。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuUserSettingService {

    /**
     * 读取用户配置，无记录时返回默认值。
     *
     * @param userId 系统用户 ID
     * @return 详情数据
     */
    AliyuUserConfigDTO get(Long userId);

    /**
     * 覆盖用户配置。
     * <p>
     * 写入前以传入的凭证束调用 {@code /user/loginUser} 校验有效性，校验失败则抛出
     * {@link cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum#CREDENTIAL_INVALID}。
     * </p>
     *
     * @param req    新配置
     * @param userId 系统用户 ID
     */
    void update(AliyuUserSettingReqDTO req, Long userId);
}
