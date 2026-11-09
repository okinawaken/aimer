package cn.nicomoe.aimer.study.listener;

import cn.nicomoe.aimer.study.model.event.StudyRoomAutoReserveExecuteEvent;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveExecutorService;
import cn.nicomoe.aimer.util.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 自动预约执行事件监听器。
 * <p>
 * 异步监听任务服务与调度器发布的执行事件，转交执行器处理。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StudyRoomAutoReserveExecuteListener {

    private final StudyRoomAutoReserveExecutorService executorService;

    /**
     * 处理自动预约执行事件。
     *
     * @param event 执行事件
     */
    @Async
    @EventListener
    public void onExecuteEvent(StudyRoomAutoReserveExecuteEvent event) {
        log.info("收到自动预约执行事件：event={}", GsonUtils.toJson(event));
        executorService.execute(event.getTaskId(), event.getTriggerType());
    }
}
