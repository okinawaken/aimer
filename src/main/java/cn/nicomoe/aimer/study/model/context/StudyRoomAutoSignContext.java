package cn.nicomoe.aimer.study.model.context;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 自动签到执行上下文
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@RequiredArgsConstructor
public class StudyRoomAutoSignContext {

    /**
     * 系统用户 ID
     */
    private final Long userId;

    /**
     * 待签到预约
     */
    private StudyRoomUserReserveClientRespDTO reserveClientRespDTO;

    /**
     * 签到门店
     */
    private StudyRoomVenueClientRespDTO venueClientRespDTO;

    /**
     * 签到扣费套餐
     */
    private StudyRoomUserPackageClientRespDTO userPackageClientRespDTO;
}
