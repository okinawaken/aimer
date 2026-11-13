package cn.nicomoe.aimer.study.service.autosignout;

/**
 * 自动签退执行器
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomAutoSignOutExecutorService {

    /**
     * 为指定用户执行一次自动签退
     *
     * @param userId 用户 ID
     */
    void execute(Long userId);
}
