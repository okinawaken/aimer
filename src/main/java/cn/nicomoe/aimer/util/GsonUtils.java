package cn.nicomoe.aimer.util;

import cn.nicomoe.aimer.config.gson.deserializer.LocalTimeDeserializer;
import cn.nicomoe.aimer.config.gson.serializer.LocalTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import xyz.erupt.core.config.GsonFactory;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 统一 JSON 序列化 / 反序列化工具。
 * <p>
 * 全项目代码级的序列化统一走这里，且与 HTTP 响应用的是同一个 Gson 实例
 * （由 {@code GsonConverterCustomizer} 注入到消息转换器），做到"同一套配置"。
 * 该 Gson 复用 Erupt 的 {@link GsonFactory#getGsonBuilder()}（已注册 Erupt 全部类型适配器），
 * 仅在此基础上追加 {@link LocalTime} 适配器。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GsonUtils {

    private static final Gson GSON = buildGson();

    private static Gson buildGson() {
        GsonBuilder gsonBuilder = GsonFactory.getGsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer());
        return gsonBuilder.create();
    }

    /**
     * 获取项目统一 Gson 实例。
     *
     * @return Gson 实例
     */
    public static Gson getGson() {
        return GSON;
    }

    /**
     * 将对象序列化为 JSON 字符串。
     *
     * @param obj 待序列化对象
     * @return JSON 字符串，入参为 {@code null} 时返回 {@code null}
     */
    public static String toJson(Object obj) {
        return Objects.isNull(obj) ? null : GSON.toJson(obj);
    }

    /**
     * 将 JSON 字符串反序列化为指定类型。
     *
     * @param json JSON 字符串
     * @param type 目标类型
     * @param <T>  目标类型泛型
     * @return 反序列化对象，入参为空时返回 {@code null}
     */
    public static <T> T fromJson(String json, Class<T> type) {
        if (Objects.isNull(json) || json.isEmpty() || Objects.isNull(type)) {
            return null;
        }
        return GSON.fromJson(json, type);
    }

    /**
     * 将 JSON 字符串反序列化为指定泛型类型（如集合、泛型包装）。
     *
     * @param json JSON 字符串
     * @param type 目标类型
     * @param <T>  目标类型泛型
     * @return 反序列化对象，入参为空时返回 {@code null}
     */
    public static <T> T fromJson(String json, Type type) {
        if (Objects.isNull(json) || json.isEmpty() || Objects.isNull(type)) {
            return null;
        }
        return GSON.fromJson(json, type);
    }
}
