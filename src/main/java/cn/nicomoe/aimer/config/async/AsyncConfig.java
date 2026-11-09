package cn.nicomoe.aimer.config.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 自习室模块异步与定时任务配置。
 * <p>
 * 开启 {@link EnableAsync} 以支持执行事件的异步监听，开启 {@link EnableScheduling} 以驱动自动预约、自动签到调度器。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@EnableAsync
@EnableScheduling
@Configuration
public class AsyncConfig {
}
