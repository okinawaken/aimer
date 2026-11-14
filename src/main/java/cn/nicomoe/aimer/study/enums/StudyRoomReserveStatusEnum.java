package cn.nicomoe.aimer.study.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/** 预约状态（reserve/check 返回的 reserveState） */
@Getter
@RequiredArgsConstructor
public enum StudyRoomReserveStatusEnum implements BaseEnum<Integer> {

    /** 0 可预约 */
    CAN(0, "可预约"),

    /** 1 不可预约 */
    CANNOT(1, "不可预约");

    private final Integer value;

    private final String description;

    /** 是否可预约 */
    public static boolean isCanReserve(Integer value) {
        return Objects.equals(CAN.getValue(), value);
    }
}
