package cn.nicomoe.aimer.study.service.autosignout;

/**
 * 自动签退执行器
 */
public interface StudyRoomAutoSignOutExecutorService {

    /**
     * 为指定用户执行一次自动签退
     */
    void execute(Long userId);
}
