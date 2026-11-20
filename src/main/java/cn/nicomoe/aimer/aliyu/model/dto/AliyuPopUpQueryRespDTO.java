package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;
import java.util.List;

/**
 * 弹窗查询响应（{@code POST /aliyuPopUp/query}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuPopUpQueryRespDTO {

    /**
     * 弹窗 ID（曝光上报的 {@code popUpId}）
     */
    private String id;

    /**
     * 弹窗图片地址
     */
    private String imageUrl;

    /**
     * 热区配置，兼容数组与 JSON 字符串两种下发格式。 坐标为百分比，支持小数；解析失败或非数组时视为无热区。
     */
    @JsonDeserialize(using = HotZoneListDeserializer.class)
    private List<HotZone> hotZone;

    /**
     * 弹窗热区。坐标为百分比；{@code actionType}：1 跳转（需 {@code jumpUrl}）、2 关闭。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HotZone {

        /**
         * 热区 ID（渲染列表 key）
         */
        private String id;

        /**
         * 热区行为：1 跳转、2 关闭
         */
        private Integer actionType;

        /**
         * 跳转地址（{@code actionType=1} 时使用）
         */
        private String jumpUrl;

        /**
         * 左偏移百分比
         */
        private Double x;

        /**
         * 上偏移百分比
         */
        private Double y;

        /**
         * 宽度百分比
         */
        private Double width;

        /**
         * 高度百分比
         */
        private Double height;
    }

    /**
     * {@code hotZone} 解析器，兼容 JSON 字符串与数组。 字符串解析失败或结果非数组时返回 {@code null}，不抛异常。
     */
    public static class HotZoneListDeserializer extends JsonDeserializer<List<HotZone>> {

        private static final ObjectMapper MAPPER = new ObjectMapper();

        private static final JavaType HOT_ZONE_LIST_TYPE = MAPPER.getTypeFactory()
                .constructCollectionType(List.class, HotZone.class);

        @Override
        public List<HotZone> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            JsonNode node = parser.readValueAsTree();
            if (node.isTextual()) {
                String raw = node.asText();
                if (raw.isBlank()) {
                    return null;
                }
                try {
                    JsonNode parsed = MAPPER.readTree(raw);
                    return parsed.isArray() ? MAPPER.convertValue(parsed, HOT_ZONE_LIST_TYPE) : null;
                } catch (IOException | IllegalArgumentException e) {
                    return null;
                }
            }
            if (!node.isArray()) {
                return null;
            }
            return MAPPER.convertValue(node, HOT_ZONE_LIST_TYPE);
        }
    }
}