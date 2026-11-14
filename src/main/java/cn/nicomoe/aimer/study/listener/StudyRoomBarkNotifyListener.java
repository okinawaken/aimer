package cn.nicomoe.aimer.study.listener;

import cn.nicomoe.aimer.study.model.event.StudyRoomBarkNotifyEvent;
import cn.nicomoe.aimer.study.service.notify.StudyRoomBarkNotifyService;
import cn.nicomoe.aimer.util.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/** Bark 通知事件监听器：异步推送，避免阻塞执行器。 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StudyRoomBarkNotifyListener {

    private final StudyRoomBarkNotifyService barkNotifyService;

    /** 处理 Bark 通知事件。 */
    @Async
    @EventListener
    public void onNotifyEvent(StudyRoomBarkNotifyEvent event) {
        log.info("收到 Bark 通知事件：event={}", GsonUtils.toJson(event));
        barkNotifyService.send(event.getUserId(), event.getTitle(), event.getBody());
    }
}
