package cn.nicomoe.aimer.config.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.Objects;

/**
 * {@link LocalTime} 的 Gson 序列化器。
 * <p>
 * Erupt 内置的 Gson 未适配 {@code LocalTime}，序列化时会尝试反射其私有字段而失败。
 * 本序列化器按 ISO 文本（如 {@code 08:00}）输出，由 {@link cn.nicomoe.aimer.util.GsonUtils} 统一注册。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
public class LocalTimeSerializer implements JsonSerializer<LocalTime> {

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        return Objects.isNull(src) ? null : new JsonPrimitive(src.toString());
    }
}
