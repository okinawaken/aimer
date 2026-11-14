package cn.nicomoe.aimer.enums;

/** 枚举通用接口：统一提供值与描述。 */
public interface BaseEnum<T> {

    /** 获取枚举值。 返回：枚举值 */
    T getValue();

    /** 获取枚举描述。 返回：枚举描述 */
    String getDescription();
}
