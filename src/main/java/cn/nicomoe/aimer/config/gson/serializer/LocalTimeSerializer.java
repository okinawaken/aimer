package cn.nicomoe.aimer.config.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.Objects;

/** {@link LocalTime} 的 Gson 序列化器：按 ISO 文本输出（Erupt 内置 Gson 会反射私有字段报错）。 */
public class LocalTimeSerializer implements JsonSerializer<LocalTime> {

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        return Objects.isNull(src) ? null : new JsonPrimitive(src.toString());
    }
}
