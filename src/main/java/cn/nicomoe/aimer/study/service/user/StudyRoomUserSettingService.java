package cn.nicomoe.aimer.study.service.user;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSettingReqDTO;

import java.util.List;

/**
 * 自习室用户设置服务。
 * <p>
 * 用户设置以系统用户 ID 作为资源 ID，详情与修改接口的 ID 参数即为用户 ID。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomUserSettingService {

    /**
     * 读取用户配置，无记录时返回默认值。
     *
     * @param userId 系统用户 ID
     * @return 详情数据
     */
    StudyRoomUserConfigDTO get(Long userId);

    /**
     * 覆盖用户配置。
     *
     * @param req    新配置
     * @param userId 系统用户 ID
     */
    void update(StudyRoomUserSettingReqDTO req, Long userId);

    /**
     * 读取全部用户配置（用于自动签到扫描）。
     * <p>
     * 仅包含已落库的配置；未落库的用户不在结果中，其默认值请用 {@link #get(Long)} 获取。
     * </p>
     *
     * @return 列表数据
     */
    List<StudyRoomUserConfigDTO> listAllConfigs();
}
