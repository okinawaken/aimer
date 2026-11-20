package cn.nicomoe.aimer.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import io.github.linpeilie.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import xyz.erupt.core.util.EruptSpringUtil;

import java.util.List;
import java.util.Map;

/**
 * MapStruct-Plus 转换工具，用法见 <a href="https://mapstruct.plus/introduction/quick-start.html">mapstruct-plus</a>。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapstructUtils {

    private static final Converter CONVERTER = EruptSpringUtil.getBean(Converter.class);

    /**
     * 将 T 类型对象，转换为 desc 类型的对象并返回
     */
    public static <T, V> V convert(T source, Class<V> desc) {
        if (ObjectUtil.isNull(source)) {
            return null;
        }
        if (ObjectUtil.isNull(desc)) {
            return null;
        }
        return CONVERTER.convert(source, desc);
    }

    /**
     * 将 T 类型对象，按照配置的映射字段规则，给 desc 类型的对象赋值并返回 desc 对象
     */
    public static <T, V> V convert(T source, V desc) {
        if (ObjectUtil.isNull(source)) {
            return null;
        }
        if (ObjectUtil.isNull(desc)) {
            return null;
        }
        return CONVERTER.convert(source, desc);
    }

    /**
     * 将 T 类型的集合，转换为 desc 类型的集合并返回
     */
    public static <T, V> List<V> convert(List<T> sourceList, Class<V> desc) {
        if (ObjectUtil.isNull(sourceList)) {
            return null;
        }
        if (CollUtil.isEmpty(sourceList)) {
            return CollUtil.newArrayList();
        }
        return CONVERTER.convert(sourceList, desc);
    }

    /**
     * 将 Map 转换为 beanClass 类型的集合并返回参数 beanClass：bean类返回：bean对象
     */
    public static <T> T convert(Map<String, Object> map, Class<T> beanClass) {
        if (MapUtil.isEmpty(map)) {
            return null;
        }
        if (ObjectUtil.isNull(beanClass)) {
            return null;
        }
        return CONVERTER.convert(map, beanClass);
    }

}
