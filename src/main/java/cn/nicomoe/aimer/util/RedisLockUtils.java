package cn.nicomoe.aimer.util;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Redisson 分布式锁工具类（纯静态，无实例状态）。
 * <p>加锁与释放需在同一线程内完成，推荐「先 tryLock 再在 finally 中 unlock」。</p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RedisLockUtils {

    /**
     * 默认锁过期时间（毫秒）
     */
    private static final long DEFAULT_EXPIRE_TIME = 10000L;

    /**
     * 默认获取锁超时时间（毫秒）
     */
    private static final long DEFAULT_WAIT_TIME = 5000L;

    private static final RedissonClient CLIENT = SpringUtil.getBean(RedissonClient.class);

    /**
     * 获取锁
     */
    private static RLock getLock(String key) {
        return CLIENT.getLock(key);
    }

    /**
     * 尝试获取锁。
     */
    public static boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) {
        try {
            return getLock(key).tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 尝试获取锁（不等待）。
     */
    public static boolean tryLock(String key, long leaseTime, TimeUnit unit) {
        return tryLock(key, 0, leaseTime, unit);
    }

    /**
     * 尝试获取锁（默认等待和过期时间）。
     */
    public static boolean tryLock(String key) {
        return tryLock(key, DEFAULT_WAIT_TIME, DEFAULT_EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 尝试获取锁（启用看门狗自动续期机制）。
     */
    public static boolean tryLockWithWatchdog(String key, long waitTime, TimeUnit unit) {
        // 传入 -1 表示使用看门狗机制
        return tryLock(key, waitTime, -1, unit);
    }

    /**
     * 尝试获取锁（启用看门狗自动续期机制，默认时间单位为毫秒）。
     */
    public static boolean tryLockWithWatchdog(String key, long waitTime) {
        return tryLockWithWatchdog(key, waitTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 尝试获取锁（启用看门狗自动续期机制，使用默认超时时间）。
     */
    public static boolean tryLockWithWatchdog(String key) {
        return tryLockWithWatchdog(key, DEFAULT_WAIT_TIME);
    }

    /**
     * 检查当前线程是否持有锁。
     */
    public static boolean isLocked(String key) {
        return getLock(key).isHeldByCurrentThread();
    }

    /**
     * 释放锁，仅当由当前线程持有时才释放，可安全放入 finally。
     */
    public static void unlock(String key) {
        RLock lock = getLock(key);
        if (lock.isHeldByCurrentThread()) {
            try {
                lock.unlockAsync().get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception ignored) {
            }
        }
    }
}
