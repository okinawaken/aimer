package cn.nicomoe.aimer.study.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * 自动签到开关
 */
@Getter
@RequiredArgsConstructor
public enum StudyRoomAutoSignInEnableEnum implements BaseEnum<Integer> {

    /**
     * 关闭：不参与自动签到
     */
    DISABLED(0, "关闭"),

    /**
     * 开启：定时扫描待签到预约并自动签到
     */
    ENABLED(1, "开启");

    private final Integer value;

    private final String description;

    /**
     * 是否开启自动签到
     */
    public static boolean isEnabled(Integer value) {
        return Objects.equals(ENABLED.getValue(), value);
    }
}
