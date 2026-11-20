package cn.nicomoe.aimer.study.listener;

import cn.nicomoe.aimer.study.model.event.StudyRoomAutoSignExecuteEvent;
import cn.nicomoe.aimer.study.service.autosign.StudyRoomAutoSignExecutorService;
import cn.nicomoe.aimer.util.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 自动签到执行事件监听器：异步转交执行器。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignExecuteListener {

    private final StudyRoomAutoSignExecutorService executorService;

    /**
     * 处理自动签到执行事件。
     */
    @Async
    @EventListener
    public void onExecuteEvent(StudyRoomAutoSignExecuteEvent event) {
        log.info("收到自动签到执行事件：event={}", GsonUtils.toJson(event));
        executorService.execute(event.getUserId());
    }
}
