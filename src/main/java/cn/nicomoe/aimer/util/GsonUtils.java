package cn.nicomoe.aimer.util;

import cn.hutool.core.util.StrUtil;
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
 * 统一 JSON 工具：复用 Erupt 的 {@link GsonFactory#getGsonBuilder()}（已含其全部适配器）， 只追加 {@link LocalTime} 适配器，与 HTTP 响应用同一个 Gson 实例。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GsonUtils {

    private static final Gson GSON = buildGson();

    /**
     * 获取项目统一 Gson 实例。 返回：Gson 实例
     */
    public static Gson getGson() {
        return GSON;
    }

    /**
     * 将对象序列化为 JSON 字符串。 返回：JSON 字符串，入参为 {@code null} 时返回 {@code null}
     */
    public static String toJson(Object obj) {
        return Objects.isNull(obj) ? null : GSON.toJson(obj);
    }

    /**
     * 将 JSON 字符串反序列化为指定类型。 参数 json：JSON 字符串参数 type：目标类型参数 <T>：目标类型泛型返回：反序列化对象，入参为空时返回 {@code null}
     */
    public static <T> T fromJson(String json, Class<T> type) {
        if (StrUtil.isEmpty(json) || Objects.isNull(type)) {
            return null;
        }
        return GSON.fromJson(json, type);
    }

    /**
     * 将 JSON 字符串反序列化为指定泛型类型（如集合、泛型包装）。 参数 json：JSON 字符串参数 type：目标类型参数 <T>：目标类型泛型返回：反序列化对象，入参为空时返回 {@code null}
     */
    public static <T> T fromJson(String json, Type type) {
        if (StrUtil.isEmpty(json) || Objects.isNull(type)) {
            return null;
        }
        return GSON.fromJson(json, type);
    }

    private static Gson buildGson() {
        GsonBuilder gsonBuilder = GsonFactory.getGsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer());
        return gsonBuilder.create();
    }
}
