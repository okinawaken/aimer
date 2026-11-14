package cn.nicomoe.aimer.aliyu.repository;

import cn.nicomoe.aimer.aliyu.model.entity.AliyuUserSettingDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AliyuUserSettingRepository extends JpaRepository<AliyuUserSettingDO, Long> {

    /** 按用户 ID 查询配置。 返回：用户配置，不存在时返回 {@code null} */
    AliyuUserSettingDO findFirstByUserId(Long userId);
}
