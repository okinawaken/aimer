package cn.nicomoe.aimer.config.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 开启异步事件监听与定时任务（自动预约、签到、签退调度器依赖）。
 */
@EnableAsync
@EnableScheduling
@Configuration
public class AsyncConfig {
}
