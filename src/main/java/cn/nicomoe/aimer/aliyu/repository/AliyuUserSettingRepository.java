package cn.nicomoe.aimer.aliyu.repository;

import cn.nicomoe.aimer.aliyu.model.entity.AliyuUserSettingDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 阿里鱼用户设置数据访问层。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuUserSettingRepository extends JpaRepository<AliyuUserSettingDO, Long> {

    /**
     * 按用户 ID 查询配置。
     *
     * @param userId 用户 ID
     * @return 用户配置，不存在时返回 {@code null}
     */
    AliyuUserSettingDO findFirstByUserId(Long userId);
}
