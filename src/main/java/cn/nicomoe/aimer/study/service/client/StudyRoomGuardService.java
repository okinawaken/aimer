package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomGuardListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomGuardOpenClientReqDTO;

public interface StudyRoomGuardService {

    /** 门店门禁区域列表。 */
    StudyRoomGuardListClientRespDTO guardSelect(Integer venueId);

    /** 门禁开门。 */
    void guardOpen(StudyRoomGuardOpenClientReqDTO form);
}
