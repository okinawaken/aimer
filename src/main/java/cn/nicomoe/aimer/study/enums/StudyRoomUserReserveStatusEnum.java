package cn.nicomoe.aimer.study.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * 我的预约状态（{@code /mp/user/userReserve/list} 的 {@code reserveStatusDesc}）。
 * <p>
 * 上游只返回状态描述文本、不返回状态码，故枚举值直接取描述文本，用于筛选时与原文匹配。
 * 取值来自真实接口数据（{@code /erupt-api/study/reserve/user/list}）：
 * 待签到、已签到、已取消、爽约系统取消。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Getter
@RequiredArgsConstructor
public enum StudyRoomUserReserveStatusEnum implements BaseEnum<String> {

    /**
     * 待签到（已预约未到店）
     */
    PENDING_SIGN("待签到", "待签到"),

    /**
     * 已签到（已到店签到）
     */
    SIGNED("已签到", "已签到"),

    /**
     * 已取消（用户主动取消）
     */
    CANCELLED("已取消", "已取消"),

    /**
     * 爽约系统取消（未按时签到，系统自动取消）
     */
    NO_SHOW_CANCELLED("爽约系统取消", "爽约系统取消");

    private final String value;

    private final String description;

    /**
     * 是否为「已取消」类状态（用户取消或系统取消）
     *
     * @param statusDesc 预约状态描述
     * @return 是否已取消
     */
    public static boolean isCancelled(String statusDesc) {
        return Objects.equals(CANCELLED.getValue(), statusDesc)
                || Objects.equals(NO_SHOW_CANCELLED.getValue(), statusDesc);
    }
}
