package cn.nicomoe.aimer.study.repository;

import cn.nicomoe.aimer.study.model.entity.StudyRoomUserSettingDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 自习室用户设置数据访问层。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomUserSettingRepository extends JpaRepository<StudyRoomUserSettingDO, Long> {

    /**
     * 按用户 ID 查询配置。
     *
     * @param userId 用户 ID
     * @return 用户配置，不存在时返回 {@code null}
     */
    StudyRoomUserSettingDO findFirstByUserId(Long userId);
}
