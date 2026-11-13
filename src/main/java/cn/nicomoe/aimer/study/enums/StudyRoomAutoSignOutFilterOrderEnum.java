package cn.nicomoe.aimer.study.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 自动签退过滤器执行顺序
 * <p>
 * 数值越小越先执行，供 {@code StudyRoomAutoSignOutFilter} 实现类声明自身顺序。
 * <p>
 * 注意：{@link #VENUE} 必须早于 {@link #SESSION}，因为签到首页接口需要门店经纬度作为入参。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Getter
@RequiredArgsConstructor
public enum StudyRoomAutoSignOutFilterOrderEnum implements BaseEnum<Integer> {

    /**
     * 已签到预约到期校验
     */
    SIGNED_RESERVE_END(100, "已签到预约到期校验"),

    /**
     * 门店坐标解析
     */
    VENUE(200, "门店坐标解析"),

    /**
     * 在座签到记录查询
     */
    SESSION(300, "在座签到记录查询");

    /**
     * 枚举值，值越小越先执行
     */
    private final Integer value;

    /**
     * 枚举描述
     */
    private final String description;
}
