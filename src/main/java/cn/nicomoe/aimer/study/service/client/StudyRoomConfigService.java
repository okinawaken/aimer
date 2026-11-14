package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomConfigClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomNoticeClientRespDTO;

public interface StudyRoomConfigService {

    /** 小程序全局配置。 */
    StudyRoomConfigClientRespDTO sysConfig();

    /** 公告栏。 */
    StudyRoomNoticeClientRespDTO noticeBoard(Integer venueId);
}
