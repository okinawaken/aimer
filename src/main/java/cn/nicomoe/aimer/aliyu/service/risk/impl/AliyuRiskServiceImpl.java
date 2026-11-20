package cn.nicomoe.aimer.aliyu.service.risk.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.service.risk.AliyuRiskService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Strictness;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 阿里鱼小程序风控头计算服务实现。 <p>{@code coeus} 按请求路径子串匹配高风险接口清单。 <p>对齐小程序 v37 模块 93323 的 hecate 算法：请求明文按 JS 语义规范化、按 {@code localeCompare} 排序键、按 {@code JSON.stringify} 约定序列化，再以 UTF-16 码元视角编码后取 MD5。不复用 Erupt 数字适配器，避免在应用 JS 双精度语义前改变数值。 <p>{@code localeCompare} 与 {@code Number::toString} 语义由本地实现完成。
 */
@Service
public class AliyuRiskServiceImpl implements AliyuRiskService {

    private static final long JS_ARRAY_INDEX_BOUND_EXCLUSIVE = 0xFFFFFFFFL;
    private static final long NON_ARRAY_INDEX_SENTINEL = Long.MAX_VALUE;
    private static final int HEX_ESCAPE_WIDTH = 4;
    private static final char CONTROL_CHAR_BOUND = 0x20;
    private static final int UTF8_SINGLE_BYTE_BOUND = 0x80;
    private static final int UTF8_TWO_BYTE_BOUND = 0x800;
    private static final int UTF8_TWO_BYTE_PREFIX = 0xC0;
    private static final int UTF8_THREE_BYTE_PREFIX = 0xE0;
    private static final int UTF8_CONTINUATION_MASK = 0x3F;
    private static final int UTF8_CONTINUATION_FLAG = 0x80;
    private static final int DOUBLE_ROUND_TRIP_PRECISION = 17;
    private static final int ECMA_FIXED_POINT_MAX_N = 21;
    private static final int ECMA_EXPONENT_FORM_MIN_N = -6;
    private static final int NON_TABLE_ORDER_BASE = 0x10000;
    private static final String PROTO_KEY = "__proto__";
    private static final String COEUS_LEVEL_HIGH = "10010";
    private static final String COEUS_LEVEL_DEFAULT = "10086";
    private static final int[] COLLATION_ORDER = new int[128];
    private static final Gson SIGN_GSON = new GsonBuilder().serializeNulls().serializeSpecialFloatingPointValues().create();
    private static final Gson JSON_PARSER = new GsonBuilder().setStrictness(Strictness.STRICT).create();
    private static final char[] COLLATION_SEQUENCE = new char[]{
            ' ', '_', '-', ',', ';', ':', '!', '?', '.', '\'', '"', '(', ')', '[', ']', '{', '}',
            '@', '*', '/', '\\', '&', '#', '%', '`', '^', '+', '<', '=', '>', '|', '~', '$',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H',
            'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P',
            'q', 'Q', 'r', 'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', 'X',
            'y', 'Y', 'z', 'Z'
    };
    private static final Set<String> COEUS_HIGH_PATHS = Set.of(
            "/user/loginUser",
            "/pro/projects",
            "/pro/eventDiscount",
            "/pro/prices",
            "/order/detail",
            "/user/userCenter",
            "/user/getDis",
            "/region/getRegionsByParentId",
            "/user/addBearer",
            "/user/modifyDis",
            "/user/modifyUser",
            "/user/userInfoGroupDesensitization",
            "/user/modifyUserBrief"
    );

    static {
        for (int i = 0; i < COLLATION_ORDER.length; i++) {
            COLLATION_ORDER[i] = NON_TABLE_ORDER_BASE + i;
        }
        for (int pos = 0; pos < COLLATION_SEQUENCE.length; pos++) {
            COLLATION_ORDER[COLLATION_SEQUENCE[pos]] = pos;
        }
    }


    @Override
    public String calcCoeus(String path) {
        if (StrUtil.isBlank(path)) {
            return COEUS_LEVEL_DEFAULT;
        }
        return COEUS_HIGH_PATHS.stream().anyMatch(path::contains) ? COEUS_LEVEL_HIGH : COEUS_LEVEL_DEFAULT;
    }

    @Override
    public String calcEpeius() {
        return Convert.toStr(System.currentTimeMillis());
    }

    @Override
    public String calcHecate(Object data, String epeius) {
        Object canonical = this.canon(this.normalize(data));
        String timestamp = StrUtil.isEmpty(epeius) ? this.calcEpeius() : epeius;
        Object signed = this.appendTimestamp(canonical, timestamp);
        StringBuilder json = new StringBuilder();
        this.stringify(signed, json);
        return DigestUtil.md5Hex(this.encodeMd5Input(json.toString()));
    }

    private Object appendTimestamp(Object canonical, String timestamp) {
        if (canonical instanceof Map<?, ?> map) {
            Map<String, Object> result = new LinkedHashMap<>(map.size() + 1);
            map.forEach((key, value) -> result.put(Convert.toStr(key), value));
            result.put(AliyuApiConstants.RequestHeader.EPEIUS, timestamp);
            return result;
        }
        if (canonical instanceof List<?> list) {
            List<Object> result = new ArrayList<>(list);
            result.add(timestamp);
            return result;
        }
        return canonical + timestamp;
    }

    private JsonElement normalize(Object data) {
        JsonElement value = SIGN_GSON.toJsonTree(data);
        if (this.isFalsy(value)) {
            return new JsonObject();
        }
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
            return this.normalizeString(value.getAsString());
        }
        return this.jsonClone(value);
    }

    private JsonElement normalizeString(String raw) {
        if (StrUtil.isBlank(raw)) {
            return new JsonObject();
        }
        try {
            JsonElement parsed = JSON_PARSER.fromJson(raw, JsonElement.class);
            return Objects.isNull(parsed) ? JsonNull.INSTANCE : parsed;
        } catch (JsonSyntaxException exception) {
            return new JsonObject();
        }
    }

    private JsonElement jsonClone(JsonElement value) {
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()
                && !Double.isFinite(value.getAsDouble())) {
            return JsonNull.INSTANCE;
        }
        if (value.isJsonObject()) {
            JsonObject result = new JsonObject();
            value.getAsJsonObject().entrySet()
                    .forEach(entry -> result.add(entry.getKey(), this.jsonClone(entry.getValue())));
            return result;
        }
        if (value.isJsonArray()) {
            JsonArray result = new JsonArray();
            value.getAsJsonArray().forEach(item -> result.add(this.jsonClone(item)));
            return result;
        }
        return value;
    }

    private boolean isFalsy(JsonElement value) {
        if (value.isJsonNull()) {
            return true;
        }
        if (!value.isJsonPrimitive()) {
            return false;
        }
        JsonPrimitive primitive = value.getAsJsonPrimitive();
        if (primitive.isBoolean()) {
            return !primitive.getAsBoolean();
        }
        if (primitive.isNumber()) {
            double number = primitive.getAsDouble();
            return number == 0 || Double.isNaN(number);
        }
        return StrUtil.isEmpty(primitive.getAsString());
    }

    private Object canon(JsonElement value) {
        if (value.isJsonObject()) {
            return this.canonObject(this.jsonClone(value).getAsJsonObject());
        }
        if (value.isJsonArray()) {
            return this.canonArray(this.jsonClone(value).getAsJsonArray());
        }
        return this.leaf(value);
    }

    private Object canonProperty(JsonElement value) {
        if (value.isJsonArray()) {
            List<Object> result = new ArrayList<>(value.getAsJsonArray().size());
            value.getAsJsonArray().forEach(item -> result.add(this.canon(item)));
            return result;
        }
        return this.canon(value);
    }

    private Object canonObject(JsonObject object) {
        List<String> keys = new ArrayList<>(object.keySet());
        keys.sort(this::localeCompare);
        Map<String, Object> result = new LinkedHashMap<>(keys.size());
        for (String key : keys) {
            if (!Objects.equals(key, PROTO_KEY)) {
                result.put(key, this.canonProperty(object.get(key)));
            }
        }
        return result;
    }

    private Object canonArray(JsonArray array) {
        List<String> indices = new ArrayList<>(array.size());
        for (int index = 0; index < array.size(); index++) {
            indices.add(Convert.toStr(index));
        }
        indices.sort(this::localeCompare);
        List<Object> result = new ArrayList<>(indices.size());
        for (String index : indices) {
            result.add(this.canonProperty(array.get(Integer.parseInt(index))));
        }
        return result;
    }

    private String leaf(JsonElement value) {
        if (value.isJsonNull()) {
            return "null";
        }
        JsonPrimitive primitive = value.getAsJsonPrimitive();
        if (primitive.isNumber()) {
            double number = primitive.getAsDouble();
            return Double.isFinite(number) ? this.jsNumber(number) : Convert.toStr(number);
        }
        return primitive.getAsString();
    }

    private void stringify(Object value, StringBuilder json) {
        if (value instanceof Map<?, ?> map) {
            this.stringifyObject(map, json);
        } else if (value instanceof List<?> list) {
            this.stringifyArray(list, json);
        } else {
            this.quote(Convert.toStr(value), json);
        }
    }

    private void stringifyObject(Map<?, ?> map, StringBuilder json) {
        List<String> keys = new ArrayList<>();
        map.keySet().forEach(key -> keys.add(Convert.toStr(key)));
        keys.sort((left, right) -> Long.compare(this.arrayIndex(left), this.arrayIndex(right)));
        json.append('{');
        boolean first = true;
        for (String key : keys) {
            if (!first) {
                json.append(',');
            }
            first = false;
            this.quote(key, json);
            json.append(':');
            this.stringify(map.get(key), json);
        }
        json.append('}');
    }

    private void stringifyArray(List<?> list, StringBuilder json) {
        json.append('[');
        for (int index = 0; index < list.size(); index++) {
            if (index > 0) {
                json.append(',');
            }
            this.stringify(list.get(index), json);
        }
        json.append(']');
    }

    private long arrayIndex(String key) {
        try {
            long index = Long.parseLong(key);
            if (index >= 0 && index < JS_ARRAY_INDEX_BOUND_EXCLUSIVE && Objects.equals(key, Convert.toStr(index))) {
                return index;
            }
            return NON_ARRAY_INDEX_SENTINEL;
        } catch (Exception e) {
            return NON_ARRAY_INDEX_SENTINEL;
        }
    }

    private void quote(String value, StringBuilder json) {
        json.append('"');
        for (int index = 0; index < value.length(); index++) {
            char ch = value.charAt(index);
            switch (ch) {
                case '"' -> json.append("\\\"");
                case '\\' -> json.append("\\\\");
                case '\b' -> json.append("\\b");
                case '\f' -> json.append("\\f");
                case '\n' -> json.append("\\n");
                case '\r' -> json.append("\\r");
                case '\t' -> json.append("\\t");
                default -> this.appendCharOrEscape(ch, value, index, json);
            }
        }
        json.append('"');
    }

    private void appendCharOrEscape(char ch, String value, int index, StringBuilder json) {
        if (this.needsEscape(ch, value, index)) {
            json.append("\\u").append(StrUtil.padPre(Integer.toHexString(ch), HEX_ESCAPE_WIDTH, '0'));
        } else {
            json.append(ch);
        }
    }

    private boolean needsEscape(char ch, String value, int index) {
        if (ch < CONTROL_CHAR_BOUND) {
            return true;
        }
        if (!Character.isSurrogate(ch)) {
            return false;
        }
        return !this.isPairedSurrogate(ch, value, index);
    }

    private boolean isPairedSurrogate(char ch, String value, int index) {
        boolean highPaired = Character.isHighSurrogate(ch)
                && index + 1 < value.length()
                && Character.isLowSurrogate(value.charAt(index + 1));
        boolean lowPaired = Character.isLowSurrogate(ch)
                && index > 0
                && Character.isHighSurrogate(value.charAt(index - 1));
        return highPaired || lowPaired;
    }

    private byte[] encodeMd5Input(String json) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        for (char ch : StrUtil.replace(json, "\r\n", "\n").toCharArray()) {
            this.encodeUtf16Char(ch, bytes);
        }
        return bytes.toByteArray();
    }

    private void encodeUtf16Char(char ch, ByteArrayOutputStream bytes) {
        if (ch < UTF8_SINGLE_BYTE_BOUND) {
            bytes.write(ch);
        } else if (ch < UTF8_TWO_BYTE_BOUND) {
            bytes.write(ch >> 6 | UTF8_TWO_BYTE_PREFIX);
            bytes.write(ch & UTF8_CONTINUATION_MASK | UTF8_CONTINUATION_FLAG);
        } else {
            bytes.write(ch >> 12 | UTF8_THREE_BYTE_PREFIX);
            bytes.write(ch >> 6 & UTF8_CONTINUATION_MASK | UTF8_CONTINUATION_FLAG);
            bytes.write(ch & UTF8_CONTINUATION_MASK | UTF8_CONTINUATION_FLAG);
        }
    }

    private int localeCompare(String left, String right) {
        int common = Math.min(left.length(), right.length());
        for (int index = 0; index < common; index++) {
            int leftOrder = this.collationOrder(Character.toLowerCase(left.charAt(index)));
            int rightOrder = this.collationOrder(Character.toLowerCase(right.charAt(index)));
            if (leftOrder != rightOrder) {
                return Integer.compare(leftOrder, rightOrder);
            }
        }
        if (left.length() != right.length()) {
            return Integer.compare(left.length(), right.length());
        }
        for (int index = 0; index < common; index++) {
            boolean leftLower = Character.isLowerCase(left.charAt(index));
            boolean rightLower = Character.isLowerCase(right.charAt(index));
            if (leftLower != rightLower) {
                return leftLower ? -1 : 1;
            }
        }
        return 0;
    }

    private int collationOrder(char ch) {
        if (ch < COLLATION_ORDER.length) {
            return COLLATION_ORDER[ch];
        }
        return NON_TABLE_ORDER_BASE + ch;
    }

    private String jsNumber(double number) {
        if (Double.isNaN(number)) {
            return "NaN";
        }
        if (Double.isInfinite(number)) {
            return number > 0 ? "Infinity" : "-Infinity";
        }
        if (number == 0) {
            return "0";
        }
        boolean negative = number < 0;
        BigDecimal stripped = this.shortestRoundTrip(negative ? -number : number);
        String digits = stripped.signum() == 0 ? "0" : stripped.unscaledValue().abs().toString();
        int digitExp = stripped.signum() == 0 ? 0 : -stripped.scale();
        String mantissa = this.formatMantissa(digits, digits.length(), digitExp + digits.length());
        return negative ? "-" + mantissa : mantissa;
    }

    private BigDecimal shortestRoundTrip(double abs) {
        BigDecimal exact = new BigDecimal(Double.toString(abs));
        for (int precision = 1; precision <= DOUBLE_ROUND_TRIP_PRECISION; precision++) {
            BigDecimal stripped = exact.round(new MathContext(precision)).stripTrailingZeros();
            if (stripped.doubleValue() == abs) {
                return stripped;
            }
        }
        return exact.round(new MathContext(DOUBLE_ROUND_TRIP_PRECISION)).stripTrailingZeros();
    }

    private String formatMantissa(String digits, int k, int n) {
        if (k <= n && n <= ECMA_FIXED_POINT_MAX_N) {
            return StrUtil.padAfter(digits, n, '0');
        }
        if (0 < n && n <= ECMA_FIXED_POINT_MAX_N) {
            return digits.substring(0, n) + "." + digits.substring(n);
        }
        if (ECMA_EXPONENT_FORM_MIN_N < n && n <= 0) {
            return "0." + StrUtil.padPre(digits, digits.length() - n, '0');
        }
        return this.formatExponential(digits, k, n - 1);
    }

    private String formatExponential(String digits, int k, int exponent) {
        StringBuilder result = new StringBuilder(digits.substring(0, 1));
        if (k > 1) {
            result.append('.').append(digits.substring(1));
        }
        result.append('e').append(exponent >= 0 ? '+' : '-').append(Math.abs(exponent));
        return result.toString();
    }

}
