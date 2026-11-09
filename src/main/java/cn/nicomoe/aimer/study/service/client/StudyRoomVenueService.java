package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAreaSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomReserveIndexQuery;

/**
 * 自习室门店域服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomVenueService {

    /**
     * 按定位查询附近门店。
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 门店列表数据
     */
    StudyRoomVenueSelectClientRespDTO venueSelect(Double currentLng, Double currentLat);

    /**
     * 预约可选门店列表。
     *
     * @return 门店列表数据
     */
    StudyRoomVenueSelectClientRespDTO reserveVenueSelect();

    /**
     * 门店详情。
     *
     * @param venueId 门店 ID
     * @return 门店详情数据
     */
    StudyRoomVenueClientRespDTO reserveVenueDetail(Integer venueId);

    /**
     * 门店详情（指定用户）。
     *
     * @param venueId 门店 ID
     * @param userId  用户 ID
     * @return 门店详情数据
     */
    StudyRoomVenueClientRespDTO reserveVenueDetail(Integer venueId, Long userId);

    /**
     * 预约首页。
     *
     * @param query 查询条件
     * @return 预约首页数据
     */
    StudyRoomReserveIndexClientRespDTO reserveIndex(StudyRoomReserveIndexQuery query);

    /**
     * 区域列表。
     *
     * @return 区域列表数据
     */
    StudyRoomAreaSelectClientRespDTO reserveAreaSelect();

    /**
     * 收银台门店列表。
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 门店列表数据
     */
    StudyRoomVenueSelectClientRespDTO receiptVenueSelect(Double currentLng, Double currentLat);

    /**
     * 抖音门店列表。
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 抖音门店列表数据
     */
    StudyRoomDouYinVenueSelectClientRespDTO douYinVenueSelect(Double currentLng, Double currentLat);
}
