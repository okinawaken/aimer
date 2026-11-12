package cn.nicomoe.aimer.study.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 自动预约过滤器执行顺序
 * <p>
 * 数值越小越先执行，供 {@code StudyRoomAutoReserveFilter} 实现类声明自身顺序。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Getter
@RequiredArgsConstructor
public enum StudyRoomAutoReserveFilterOrderEnum implements BaseEnum<Integer> {

    /**
     * 启用状态校验
     */
    ENABLED(100, "启用状态校验"),

    /**
     * 目标日期计算与当日已执行校验
     */
    TARGET_DATE(200, "目标日期计算与当日已执行校验"),

    /**
     * 执行星期校验
     */
    WEEKDAY(300, "执行星期校验"),

    /**
     * 预约门店校验
     */
    VENUE(400, "预约门店校验"),

    /**
     * 座位选择
     */
    SEAT(500, "座位选择"),

    /**
     * 预约资格校验
     */
    ELIGIBILITY(600, "预约资格校验");

    /**
     * 枚举值，值越小越先执行
     */
    private final Integer value;

    /**
     * 枚举描述
     */
    private final String description;
}
