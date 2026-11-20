package cn.nicomoe.aimer.study.client.http.v2;

import cn.nicomoe.aimer.study.client.http.StudyRoomBaseClient;
import cn.nicomoe.aimer.study.constant.StudyRoomApiConstants;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAreaSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueSelectClientRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Var;

/**
 * 门店域（显式用户上下文）：门店列表、门店详情、区域
 */
public interface StudyRoomVenueV2Client extends StudyRoomBaseClient {

    /**
     * 按定位查询附近门店（签到首页、报修页用）
     */
    @Get(url = "/mp/venue/select")
    StudyRoomClientResult<StudyRoomVenueSelectClientRespDTO> venueSelect(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 预约可选门店列表（无定位参数）
     */
    @Get(url = "/mp/reserve/venue/select")
    StudyRoomClientResult<StudyRoomVenueSelectClientRespDTO> reserveVenueSelect(@Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 门店详情
     */
    @Get(url = "/mp/reserve/venue/detail")
    StudyRoomClientResult<StudyRoomVenueClientRespDTO> reserveVenueDetail(@Query(value = "venueId") Integer venueId, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 预约首页（banner + 门店列表 + 区域筛选），channelBili 为渠道标识。
     */
    @Get(url = "/mp/reserve/index")
    StudyRoomClientResult<StudyRoomReserveIndexClientRespDTO> reserveIndex(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat, @Query(value = "venueName") String venueName, @Query(value = "channelBili") Integer channelBili, @Query(value = "areaId") Long areaId, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 区域列表（预约首页筛选）
     */
    @Get(url = "/mp/reserve/area/select")
    StudyRoomClientResult<StudyRoomAreaSelectClientRespDTO> reserveAreaSelect(@Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 收银台门店列表（美团团购核销）
     */
    @Get(url = "/mp/receipt/venue/select")
    StudyRoomClientResult<StudyRoomVenueSelectClientRespDTO> receiptVenueSelect(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 抖音门店列表（比普通门店多 poiId / poiName）
     */
    @Get(url = "/mp/douYin/venue/select")
    StudyRoomClientResult<StudyRoomDouYinVenueSelectClientRespDTO> douYinVenueSelect(@Query(value = "currentLng") Double currentLng, @Query(value = "currentLat") Double currentLat, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

}
