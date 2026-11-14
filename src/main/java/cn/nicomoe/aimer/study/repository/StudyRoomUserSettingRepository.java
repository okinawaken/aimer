package cn.nicomoe.aimer.study.repository;

import cn.nicomoe.aimer.study.model.entity.StudyRoomUserSettingDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRoomUserSettingRepository extends JpaRepository<StudyRoomUserSettingDO, Long> {

    /** 按用户 ID 查询配置。 返回：用户配置，不存在时返回 {@code null} */
    StudyRoomUserSettingDO findFirstByUserId(Long userId);
}
