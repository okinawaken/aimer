package cn.nicomoe.aimer.config.gson.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.Objects;

/**
 * {@link LocalTime} 的 Gson 反序列化器。
 * <p>
 * 按 ISO 文本（如 {@code 08:00}）解析为 {@code LocalTime}，由 {@link cn.nicomoe.aimer.util.GsonUtils} 统一注册。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
public class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (Objects.isNull(json) || json.isJsonNull()) {
            return null;
        }
        return LocalTime.parse(json.getAsString());
    }
}
