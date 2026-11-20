package cn.nicomoe.aimer.study.listener;

import cn.nicomoe.aimer.study.model.event.StudyRoomAutoSignOutExecuteEvent;
import cn.nicomoe.aimer.study.service.autosignout.StudyRoomAutoSignOutExecutorService;
import cn.nicomoe.aimer.util.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 自动签退执行事件监听器：异步转交执行器。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignOutExecuteListener {

    private final StudyRoomAutoSignOutExecutorService executorService;

    /**
     * 处理自动签退执行事件。
     */
    @Async
    @EventListener
    public void onExecuteEvent(StudyRoomAutoSignOutExecuteEvent event) {
        log.info("收到自动签退执行事件：event={}", GsonUtils.toJson(event));
        executorService.execute(event.getUserId());
    }
}
