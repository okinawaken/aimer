package cn.nicomoe.aimer.study.model.context;

import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoSignOutSessionBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
     * 今日已到期的已签到预约
     */
    private StudyRoomUserReserveClientRespDTO expiredReserveClientRespDTO;

    /**
     * 已到期预约所在门店，其经纬度作为签到首页的当前定位
     */
    private StudyRoomVenueClientRespDTO venueClientRespDTO;

    /**
     * 签到首页响应，承载全部在座签到记录
     */
    private StudyRoomIndexClientRespDTO indexClientRespDTO;

    /**
     * 待签退的在座会话执行计划
     */
    private List<StudyRoomAutoSignOutSessionBO> sessionBOList;
}
