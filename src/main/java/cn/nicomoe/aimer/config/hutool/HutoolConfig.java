package cn.nicomoe.aimer.config.hutool;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.context.annotation.Configuration;

/**
 * Hutool 相关配置。
 * <p>{@link EnableSpringUtil} 会自动导入 Hutool 的 {@code SpringUtil}。</p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Configuration
@EnableSpringUtil
public class HutoolConfig {
}
