package cn.nicomoe.aimer.study.service.user;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSettingReqDTO;

import java.util.List;

/**
 * 用户设置服务，以系统用户 ID 作为资源 ID。
 */
public interface StudyRoomUserSettingService {

    /**
     * 读取用户配置，无记录时返回默认值。
     */
    StudyRoomUserConfigDTO get(Long userId);

    /**
     * 覆盖用户配置。
     */
    void update(StudyRoomUserSettingReqDTO req, Long userId);

    /**
     * 读取全部用户配置（用于自动签到扫描），仅包含已落库的配置，未落库的用户需用 {@link #get(Long)} 取默认值。
     */
    List<StudyRoomUserConfigDTO> listAllConfigs();
}
