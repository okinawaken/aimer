package cn.nicomoe.aimer.study.service.notify;

/**
 * 自习室 Bark 通知服务。
 * <p>按 userId 解析用户配置中的 Bark 推送地址并发送通知；未配置时静默跳过。</p>
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomBarkNotifyService {

    /**
     * 向指定用户推送一条 Bark 通知。
     * <p>未配置时不做任何事；推送失败由底层吞掉，不影响调用方。</p>
     *
     * @param userId 系统用户 ID
     * @param title  通知标题
     * @param body   通知正文，允许为 {@code null}
     */
    void send(Long userId, String title, String body);
}
