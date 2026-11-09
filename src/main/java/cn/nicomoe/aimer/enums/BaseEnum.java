package cn.nicomoe.aimer.enums;

/**
 * 枚举通用接口。
 * <p>
 * 约定每一个业务枚举都持有「值」与「描述」两个属性，便于持久化与前端展示。
 * </p>
 *
 * @param <T> 枚举值类型
 * @author bixiu
 * @since 4.2.0
 */
public interface BaseEnum<T> {

    /**
     * 获取枚举值。
     *
     * @return 枚举值
     */
    T getValue();

    /**
     * 获取枚举描述。
     *
     * @return 枚举描述
     */
    String getDescription();
}
