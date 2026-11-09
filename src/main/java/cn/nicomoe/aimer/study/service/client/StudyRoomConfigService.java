package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomConfigClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomNoticeClientRespDTO;

/**
 * 自习室配置域服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomConfigService {

    /**
     * 小程序全局配置。
     *
     * @return 配置数据
     */
    StudyRoomConfigClientRespDTO sysConfig();

    /**
     * 公告栏。
     *
     * @param venueId 门店 ID
     * @return 公告数据
     */
    StudyRoomNoticeClientRespDTO noticeBoard(Integer venueId);
}
