package cn.nicomoe.aimer.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.redisson.api.RBatch;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RListAsync;
import org.redisson.api.RMap;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RTopic;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.redisson.api.options.KeysScanOptions;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Redis 工具类，基于 {@link RedissonClient}。
 *
 * @author bixiu
 * @since 4.2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RedisCacheUtils {

    private static final RedissonClient CLIENT = SpringUtil.getBean(RedissonClient.class);

    // ============================== 基本 ==============================

    public static <T> void set(String key, T value) {
        CLIENT.getBucket(key).set(value);
    }

    public static <T> void set(String key, T value, Duration duration) {
        CLIENT.getBucket(key).set(value, duration);
    }

    public static <T> boolean setIfAbsent(String key, T value) {
        return CLIENT.getBucket(key).setIfAbsent(value);
    }

    public static <T> boolean setIfAbsent(String key, T value, Duration duration) {
        return CLIENT.getBucket(key).setIfAbsent(value, duration);
    }

    public static <T> boolean setIfExists(String key, T value) {
        return CLIENT.getBucket(key).setIfExists(value);
    }

    public static <T> boolean setIfExists(String key, T value, Duration duration) {
        return CLIENT.getBucket(key).setIfExists(value, duration);
    }

    public static <T> T get(String key) {
        RBucket<T> bucket = CLIENT.getBucket(key);
        return bucket.get();
    }

    public static <T> T getOrDefault(String key, T defaultValue) {
        return ObjectUtil.defaultIfNull(get(key), defaultValue);
    }

    public static boolean delete(String key) {
        return CLIENT.getBucket(key).delete();
    }

    public static void deleteByPattern(String pattern) {
        CLIENT.getKeys().deleteByPattern(pattern);
    }

    public static long incr(String key) {
        return CLIENT.getAtomicLong(key).incrementAndGet();
    }

    public static long decr(String key) {
        return CLIENT.getAtomicLong(key).decrementAndGet();
    }

    public static boolean expire(String key, Duration duration) {
        return CLIENT.getBucket(key).expire(duration);
    }

    public static long getTimeToLive(String key) {
        return CLIENT.getBucket(key).remainTimeToLive();
    }

    public static boolean exists(String key) {
        return CLIENT.getKeys().countExists(key) > 0;
    }

    public static Collection<String> keys(String pattern) {
        KeysScanOptions options = KeysScanOptions.defaults();
        options.pattern(pattern);
        return CLIENT.getKeys().getKeysStream(options).toList();
    }

    // ============================== List ==============================

    public static <T> void setList(String key, List<T> value) {
        RList<T> list = CLIENT.getList(key);
        list.addAll(value);
    }

    public static <T> void setList(String key, List<T> value, Duration duration) {
        RBatch batch = CLIENT.createBatch();
        RListAsync<T> list = batch.getList(key);
        list.addAllAsync(value);
        list.expireAsync(duration);
        batch.execute();
    }

    public static <T> List<T> getList(String key) {
        RList<T> list = CLIENT.getList(key);
        return list.readAll();
    }

    // ============================== Hash ==============================

    public static <T> void hSet(String key, String field, T value) {
        RMap<String, T> map = CLIENT.getMap(key);
        map.put(field, value);
    }

    public static <T> T hGet(String key, String field) {
        RMap<String, T> map = CLIENT.getMap(key);
        return map.get(field);
    }

    public static <T> Map<String, T> hGetAll(String key) {
        RMap<String, T> map = CLIENT.getMap(key);
        return map.readAllMap();
    }

    public static boolean hExists(String key, String field) {
        RMap<String, ?> map = CLIENT.getMap(key);
        return map.containsKey(field);
    }

    public static long hDel(String key, String... fields) {
        RMap<String, ?> map = CLIENT.getMap(key);
        return map.fastRemove(fields);
    }

    // ============================== ZSet ==============================

    public static <T> boolean zAdd(String key, T value, double score) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.add(score, value);
    }

    public static <T> Double zScore(String key, T value) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.getScore(value);
    }

    public static <T> Integer zRank(String key, T value) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.rank(value);
    }

    public static <T> int zSize(String key) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.size();
    }

    public static <T> boolean zRemove(String key, T value) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.remove(value);
    }

    public static <T> int zRemoveRangeByScore(String key, double min, double max) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.removeRangeByScore(min, true, max, true);
    }

    public static <T> int zRemoveRangeByRank(String key, int startIndex, int endIndex) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.removeRangeByRank(startIndex, endIndex);
    }

    public static <T> Collection<T> zRangeByScore(String key, double min, double max) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.valueRange(min, true, max, true);
    }

    public static <T> Collection<T> zRangeByScore(String key, double min, double max, int offset, int count) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.valueRange(min, true, max, true, offset, count);
    }

    public static <T> int zCountRangeByScore(String key, double min, double max) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        return zSet.count(min, true, max, true);
    }

    public static <T> double zSum(String key, Collection<T> values) {
        RScoredSortedSet<T> zSet = CLIENT.getScoredSortedSet(key);
        double sum = 0;
        for (T value : values) {
            Double score = zSet.getScore(value);
            if (Objects.nonNull(score)) {
                sum += score;
            }
        }
        return sum;
    }

    // ============================== 限流 / 消息 ==============================

    public static boolean rateLimit(String key, RateType rateType, int rate, int rateInterval) {
        return rateLimit(key, rateType, rate, Duration.ofSeconds(rateInterval));
    }

    public static boolean rateLimit(String key, RateType rateType, int rate, Duration rateInterval) {
        RRateLimiter rateLimiter = CLIENT.getRateLimiter(key);
        rateLimiter.trySetRate(rateType, rate, rateInterval);
        return rateLimiter.tryAcquire(1);
    }

    public static <T> void publish(String name, T msg, Consumer<T> consumer) {
        RTopic topic = CLIENT.getTopic(name);
        topic.publish(msg);
        consumer.accept(msg);
    }

    public static <T> void publish(String name, T msg) {
        RTopic topic = CLIENT.getTopic(name);
        topic.publish(msg);
    }

    public static <T> void subscribe(String name, Class<T> clazz, Consumer<T> consumer) {
        RTopic topic = CLIENT.getTopic(name);
        topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }
}
