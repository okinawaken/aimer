package org.dromara.aimer.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 *
 * @author bixiu
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    private static ObjectMapper objectMapper = null;

    private static final String JSON_OBJECT_PREFIX = "{";
    private static final String JSON_ARRAY_PREFIX = "[";

    static {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static boolean isJsonString(String str) {
        return isJsonObjectString(str) || isJsonArrayString(str);
    }

    public static boolean isJsonObjectString(String str) {
        return StringUtils.trimToEmpty(str).startsWith(JSON_OBJECT_PREFIX);
    }

    public static boolean isJsonArrayString(String str) {
        return StringUtils.trimToEmpty(str).startsWith(JSON_ARRAY_PREFIX);
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static byte[] toJsonBytes(Object object) {
        return toJson(object).getBytes(StandardCharsets.UTF_8);
    }

    public static String toJsonIgnoreNullField(Object object) {
        return toJson(object);
    }

    public static String toPrettyJson(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T toObject(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            try {
                return objectMapper.readValue(jsonString, clazz);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static <T> T toObject(String jsonString, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            try {
                return objectMapper.readValue(jsonString, valueTypeRef);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static <T> List<T> toList(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{clazz});
            return (List) toObject(jsonString, javaType);
        }
    }

    public static <K, V> Map<K, V> toHashMap(String jsonString, Class<K> keyType, Class<V> valueType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, new Class[]{keyType, valueType});
            return (Map) toObject(jsonString, javaType);
        }
    }

    public static <V> Map<String, V> toHashMap(String jsonString, Class<V> valueType) {
        return toHashMap(jsonString, String.class, valueType);
    }

    public static Map<String, Object> toHashMap(String jsonString) {
        return toHashMap(jsonString, String.class, Object.class);
    }

    public static <T> T toObject(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            try {
                return objectMapper.readValue(jsonString, javaType);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static JsonNode selectJsonNode(String jsonString, String nodeName) {
        JsonNode jsonNode = toJsonNode(jsonString);
        return nodeName == null ? jsonNode : jsonNode.get(nodeName);
    }

    public static JsonNode toJsonNode(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static ArrayNode toJsonArrayNode(String jsonString) {
        try {
            JsonNode node = objectMapper.readTree(jsonString);
            return (ArrayNode) node;
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String getJsonNodeValue(String jsonString, String attrs) {
        return StringUtils.isBlank(jsonString) ? null : getJsonNodeValue(selectJsonNode(jsonString, (String) null), attrs);
    }

    public static <T> T toObject(JsonNode node, Class<T> clazz) {
        return toObject(node.toString(), clazz);
    }

    public static <T> List<T> toList(ArrayNode node, Class<T> clazz) {
        List<T> result = new ArrayList<>(node.size());
        for (int i = 0; i < node.size(); ++i) {
            result.add(toObject(node.get(i), clazz));
        }
        return result;
    }

    public static String getJsonNodeValue(JsonNode node, String attrs) {
        int index = attrs.indexOf(".");
        JsonNode subNode = null;
        if (index == -1) {
            if (node != null) {
                if (node instanceof ArrayNode) {
                    ArrayNode arrayNode = (ArrayNode) node;
                    subNode = arrayNode.isEmpty() ? null : arrayNode.get(0).get(attrs);
                } else {
                    subNode = node.get(attrs);
                }

                if (subNode == null) {
                    return null;
                } else {
                    return subNode instanceof ValueNode ? subNode.asText() : subNode.toString();
                }
            } else {
                return null;
            }
        } else {
            String s1 = attrs.substring(0, index);
            String s2 = attrs.substring(index + 1);
            if (node instanceof ArrayNode) {
                ArrayNode arrayNode = (ArrayNode) node;
                subNode = arrayNode.isEmpty() ? null : arrayNode.get(0).get(s1);
            } else {
                subNode = node.get(s1);
            }

            return getJsonNodeValue(subNode, s2);
        }
    }
}
