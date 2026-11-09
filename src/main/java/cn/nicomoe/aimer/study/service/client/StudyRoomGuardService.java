package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomGuardListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomGuardOpenClientReqDTO;

/**
 * 自习室门禁域服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomGuardService {

    /**
     * 门店门禁区域列表。
     *
     * @param venueId 门店 ID
     * @return 门禁区域数据
     */
    StudyRoomGuardListClientRespDTO guardSelect(Integer venueId);

    /**
     * 门禁开门。
     *
     * @param form 请求参数
     */
    void guardOpen(StudyRoomGuardOpenClientReqDTO form);
}
