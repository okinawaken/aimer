package cn.nicomoe.aimer.study.client.http.v1;

import cn.nicomoe.aimer.study.client.http.StudyRoomBaseClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAreaSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueSelectClientRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;

/**
 * 门店域：门店列表、门店详情、区域
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomVenueClient extends StudyRoomBaseClient {

    /**
     * 按定位查询附近门店（签到首页、报修页用）
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 响应数据
     */
    @Get(url = "/mp/venue/select")
    StudyRoomClientResult<StudyRoomVenueSelectClientRespDTO> venueSelect(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat);

    /**
     * 预约可选门店列表（无定位参数）
     *
     * @return 响应数据
     */
    @Get(url = "/mp/reserve/venue/select")
    StudyRoomClientResult<StudyRoomVenueSelectClientRespDTO> reserveVenueSelect();

    /**
     * 门店详情
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @Get(url = "/mp/reserve/venue/detail")
    StudyRoomClientResult<StudyRoomVenueClientRespDTO> reserveVenueDetail(@Query(value = "venueId") Integer venueId);

    /**
     * 预约首页（banner + 门店列表 + 区域筛选）
     *
     * @param currentLng  当前经度
     * @param currentLat  当前纬度
     * @param venueName   门店名称
     * @param channelBili 渠道标识
     * @param areaId      区域 ID
     * @return 响应数据
     */
    @Get(url = "/mp/reserve/index")
    StudyRoomClientResult<StudyRoomReserveIndexClientRespDTO> reserveIndex(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat, @Query(value = "venueName") String venueName, @Query(value = "channelBili") Integer channelBili, @Query(value = "areaId") Long areaId);

    /**
     * 区域列表（预约首页筛选）
     *
     * @return 响应数据
     */
    @Get(url = "/mp/reserve/area/select")
    StudyRoomClientResult<StudyRoomAreaSelectClientRespDTO> reserveAreaSelect();

    /**
     * 收银台门店列表（美团团购核销）
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 响应数据
     */
    @Get(url = "/mp/receipt/venue/select")
    StudyRoomClientResult<StudyRoomVenueSelectClientRespDTO> receiptVenueSelect(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat);

    /**
     * 抖音门店列表（比普通门店多 poiId / poiName）
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 响应数据
     */
    @Get(url = "/mp/douYin/venue/select")
    StudyRoomClientResult<StudyRoomDouYinVenueSelectClientRespDTO> douYinVenueSelect(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat);

}
