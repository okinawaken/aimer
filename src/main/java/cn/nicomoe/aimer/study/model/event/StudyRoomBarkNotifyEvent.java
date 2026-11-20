package cn.nicomoe.aimer.study.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Bark 通知事件：执行器在终态发布，用来解耦推送链路。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomBarkNotifyEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系统用户 ID，用于解析其 Bark 推送地址
     */
    private Long userId;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知正文，允许为 {@code null}
     */
    private String body;
}
