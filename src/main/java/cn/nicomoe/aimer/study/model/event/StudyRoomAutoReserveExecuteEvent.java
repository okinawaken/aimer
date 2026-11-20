package cn.nicomoe.aimer.study.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 自动预约执行事件：手动触发与调度扫描共用。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomAutoReserveExecuteEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务 ID
     */
    private Long taskId;

    /**
     * 触发方式，取值见 {@link cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveTriggerTypeEnum}
     */
    private Integer triggerType;
}
