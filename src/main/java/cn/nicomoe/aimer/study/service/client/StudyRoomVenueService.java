package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAreaSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomReserveIndexQuery;

public interface StudyRoomVenueService {

    /**
     * 按定位查询附近门店。
     */
    StudyRoomVenueSelectClientRespDTO venueSelect(Double currentLng, Double currentLat);

    /**
     * 预约可选门店列表。
     */
    StudyRoomVenueSelectClientRespDTO reserveVenueSelect();

    /**
     * 门店详情。
     */
    StudyRoomVenueClientRespDTO reserveVenueDetail(Integer venueId);

    /**
     * 门店详情（指定用户）。
     */
    StudyRoomVenueClientRespDTO reserveVenueDetail(Integer venueId, Long userId);

    /**
     * 预约首页。
     */
    StudyRoomReserveIndexClientRespDTO reserveIndex(StudyRoomReserveIndexQuery query);

    /**
     * 区域列表。
     */
    StudyRoomAreaSelectClientRespDTO reserveAreaSelect();

    /**
     * 收银台门店列表。
     */
    StudyRoomVenueSelectClientRespDTO receiptVenueSelect(Double currentLng, Double currentLat);

    /**
     * 抖音门店列表。
     */
    StudyRoomDouYinVenueSelectClientRespDTO douYinVenueSelect(Double currentLng, Double currentLat);
}
