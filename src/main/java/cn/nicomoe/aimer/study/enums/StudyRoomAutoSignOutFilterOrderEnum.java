package cn.nicomoe.aimer.study.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 自动签退过滤器执行顺序
 * <p>
 * 数值越小越先执行，供 {@code StudyRoomAutoSignOutFilter} 实现类声明自身顺序。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Getter
@RequiredArgsConstructor
public enum StudyRoomAutoSignOutFilterOrderEnum implements BaseEnum<Integer> {

    /**
     * 在座签到记录查询
     */
    SESSION(100, "在座签到记录查询"),

    /**
     * 门店坐标解析
     */
    VENUE(200, "门店坐标解析");

    /**
     * 枚举值，值越小越先执行
     */
    private final Integer value;

    /**
     * 枚举描述
     */
    private final String description;
}
