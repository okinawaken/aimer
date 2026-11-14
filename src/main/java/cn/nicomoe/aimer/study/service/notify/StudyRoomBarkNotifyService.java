package cn.nicomoe.aimer.study.service.notify;

/** Bark 推送服务：按 userId 取配置里的推送地址，未配置则静默跳过。 */
public interface StudyRoomBarkNotifyService {

    /** 向指定用户推送一条 Bark 通知，未配置时静默跳过，推送失败由底层吞掉，不影响调用方，body 允许为 {@code null}。 */
    void send(Long userId, String title, String body);
}
