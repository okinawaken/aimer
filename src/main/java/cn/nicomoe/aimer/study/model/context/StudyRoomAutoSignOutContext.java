package cn.nicomoe.aimer.study.model.context;

import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 自动签退执行上下文
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@RequiredArgsConstructor
public class StudyRoomAutoSignOutContext {

    /**
     * 系统用户 ID
     */
    private final Long userId;

    /**
     * 签到首页响应
     */
    private StudyRoomIndexClientRespDTO indexClientRespDTO;

    /**
     * 所在门店
     */
    private StudyRoomVenueClientRespDTO venueClientRespDTO;
}
