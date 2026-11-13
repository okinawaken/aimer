package cn.nicomoe.aimer.study.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Bark 通知事件。
 * <p>由自动预约 / 签到 / 签退执行器在终态发布，解耦执行器与推送链路。</p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomBarkNotifyEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 系统用户 ID，用于解析其 Bark 推送地址 */
    private Long userId;

    /** 通知标题 */
    private String title;

    /** 通知正文，允许为 {@code null} */
    private String body;
}
