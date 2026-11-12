package cn.nicomoe.aimer.study.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 自习室门店域业务常量。
 *
 * @author bixiu
 * @since 4.2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StudyRoomVenueConstants {

    /**
     * 押金专用门店名称。
     * <p>
     * 该门店仅承接押金业务，不用于预约、签到等场景，所有门店列表查询默认剔除。
     * </p>
     */
    public static final String DEPOSIT_ONLY_VENUE_NAME = "押金专用";
}
