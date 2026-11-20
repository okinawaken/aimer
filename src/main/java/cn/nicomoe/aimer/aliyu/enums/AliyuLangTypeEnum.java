package cn.nicomoe.aimer.aliyu.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 阿里鱼小程序语言类型（请求参数 {@code langType}）。
 *
 * <p>小程序把 {@code getCurrentLanguage()} 返回的系统语言归一化为 {@code zh_CN/en_US/zh_TW}，再映射为
 * {@code langType} 追加到每个请求 URL 末尾；系统语言与 {@code langType} 的对应关系为：
 * {@code zh_CN}、{@code zh-Hans} → 1；{@code en} → 2；{@code zh_TW}、{@code zh_HK}、{@code zh-Hant}、{@code zh-HK} → 3。
 */
@Getter
@RequiredArgsConstructor
public enum AliyuLangTypeEnum implements BaseEnum<String> {

    /**
     * 简体中文，小程序缺省回落值
     */
    SIMPLIFIED_CHINESE(Code.SIMPLIFIED_CHINESE, "简体中文"),

    /**
     * 英文
     */
    ENGLISH(Code.ENGLISH, "English"),

    /**
     * 繁体中文
     */
    TRADITIONAL_CHINESE(Code.TRADITIONAL_CHINESE, "繁體中文");

    private final String value;

    private final String description;

    /**
     * 语言类型值的编译期常量。注解属性只接受编译期常量，无法引用 {@link #getValue()}，注解场景请使用这里的值。
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Code {

        /**
         * 简体中文
         */
        public static final String SIMPLIFIED_CHINESE = "1";

        /**
         * 英文
         */
        public static final String ENGLISH = "2";

        /**
         * 繁体中文
         */
        public static final String TRADITIONAL_CHINESE = "3";
    }
}
